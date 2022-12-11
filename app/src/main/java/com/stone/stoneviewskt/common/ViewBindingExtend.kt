package com.stone.stoneviewskt.common

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * desc   : ViewBinding、ViewDataBinding 的生成类的实例化 封装
 *          支持dataBinding时，本页中的函数泛型 不定义成 ViewDataBinding 也可以使用，只是需要 ViewDataBinding特性时，需要强转一下类型
 * author : stone
 * email  : aa86799@163.com
 * time   : 2022/5/14 22:44
 */
/**
 * AppCompatActivity#onCreate()使用
 */
inline fun <reified VB : ViewBinding> AppCompatActivity.inflate(): VB {
    return inflateBinding<VB>(layoutInflater).apply {
        setContentView(this.root)
    }
}

/**
 * Dialog#onCreate() 使用
 */
inline fun <reified VB : ViewBinding> Dialog.inflate(): VB {
    return inflateBinding<VB>(layoutInflater).apply {
        setContentView(this.root)
    }
}

/**
 * 继承自 ViewGroup 使用。
 * 例外情况：使用<merge>布局， 生成的ViewBinding 不会生成三个参数的 inflate方法， 所以调用本方法会报错。
 *   那就不能调用本封装方法，只能调用生成目标类的，如： XxxBinding.inflate(LayoutInflater.from(context), this)
 */
inline fun <reified VB : ViewBinding> ViewGroup.inflate(viewGroup: ViewGroup, attachToRoot: Boolean): VB {
    return inflateBinding(LayoutInflater.from(context), viewGroup, attachToRoot)
}

/**
 * Recycler.Adapter#onCreateViewHolder() 使用
 * 自定义ViewGroup中使用
 * ...
 * 以前需要 传递 parent:ViewGroup 对象来进行初始化的布局，都可以调用本方法
 */
inline fun <reified VB : ViewBinding> inflate(parent: ViewGroup): VB {
    return inflateBinding(LayoutInflater.from(parent.context), parent, false)
}

/**
 * 这是一个基础方法。所有创建ViewBinding对象的地方都可以直接调用。
 * 反射调用 ViewBinding.inflate(layoutInflater, viewGroup, attachToRoot) 。
 * 对于 Fragment、DialogFragment 都直接使用本方法。
 * 调用时，viewGroup可以不传，默认为null。
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater, viewGroup: ViewGroup? = null, attachToRoot: Boolean = false): VB {
    return VB::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, layoutInflater, viewGroup, attachToRoot) as VB
}
