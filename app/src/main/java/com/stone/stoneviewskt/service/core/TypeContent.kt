package com.stone.stoneviewskt.service.core

import com.stone.stoneviewskt.service.CesManager
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

class TypeContent private constructor() {

    private val mClassMap: ConcurrentHashMap<String, Class<*>> = ConcurrentHashMap()
    private val mMethodMap: ConcurrentHashMap<Class<*>, ConcurrentHashMap<String, Method>> = ConcurrentHashMap()

    companion object {
        val instance = Holder.instance
    }

    private object Holder {
        val instance = TypeContent()
    }

    fun register(clz: Class<CesManager>) {
        registerClass(clz)
        registerMethod(clz)
    }

    private fun registerClass(clz: Class<CesManager>) {
        mClassMap.putIfAbsent(clz.name, clz)
    }

    private fun registerMethod(clz: Class<CesManager>) {
        val map = ConcurrentHashMap<String, Method>()
        clz.methods.forEach {
            map[it.name] = it
        }

        mMethodMap.putIfAbsent(clz, map)
    }
}