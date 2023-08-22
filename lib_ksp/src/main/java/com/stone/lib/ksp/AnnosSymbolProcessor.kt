package com.stone.lib.ksp

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/*
ksp guide: https://kotlinlang.org/docs/ksp-quickstart.html
 */
class AnnosSymbolProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(Hello::class.java.name).forEach {
            if (it is KSFunctionDeclaration && it.containingFile != null) {
                val typeSpec = TypeSpec.classBuilder("HelloStone")
                typeSpec.funSpecs.add(
                    FunSpec.builder("sayHello")
                        .addModifiers(KModifier.PUBLIC)
                        .addStatement("println(\"hello stone\")")
                        .addKdoc("文件生成在 build/generated/ksp/")
                        .build()
                )
                FileSpec.builder(packageName = it.packageName.asString(), "HelloStone")
                    .addType(typeSpec.build())
//                    .addFunction()
                    .build()
                    .writeTo(environment.codeGenerator, Dependencies(false, it.containingFile!!))
            }
        }

        /*
        如下 在 fileSpec 中 add function ；那么函数 hello() 就不会在 class HelloStone {} 中
        if (it is KSFunctionDeclaration && it.containingFile != null) {
                FileSpec.builder(packageName = it.packageName.asString(), "HelloStone")
                    .addType(TypeSpec.classBuilder("HelloStone").build())
                    .addFunction(FunSpec.builder("hello")
                        .addModifiers(KModifier.PRIVATE)
                        .addStatement("println(\"hello\")")
                        .build()
                    )
                    .build()
                    .writeTo(environment.codeGenerator, Dependencies(false, it.containingFile!!))
            }
         */
        return emptyList()
    }
}