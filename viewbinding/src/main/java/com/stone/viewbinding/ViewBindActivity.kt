package com.stone.viewbinding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stone.viewbinding.databinding.ActivityViewBindBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/26 19:17
 */
class ViewBindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
         * 开启ViewBinding功能后, 生成一个绑定类。
         * build/generated/data_binding_base_class_source_out/debug/out/{package}/databinding/
         * 命名规则：将layout xml 文件名，去掉下滑线，并首字母大写，最后加上 Binding，组合起来。
         * ActivityViewBindBinding.java    (点击跳转 ActivityViewBindingBinding，会到 xml中)
         * 组件的id 命名：去掉下滑线
         */
        val view = ActivityViewBindBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.activityVbTv.text = "set text with view binding"

        view.activityVbTest.setOnClickListener {
            startActivity(Intent(this, NoViewBindActivity::class.java))
        }

        /*
ViewBinding 有如下几点优势：
- Type safety：findViewById, ButterKnife 均存在类型转换问题，例如不小心将一个TextView错误的赋值给一个Button变量，
    都会报错，这一错误很容易出现，关键在错误还出现在运行时，而不是编译时！
    而ViewBinding中，产生的binding类中的属性是依据XML layout文件生成的，所以类型不会错，生成的时候已经处理好了。
- Null safety: findViewById, ButterKnife与Kotlin Android Extensions 均存在Null不安全问题。这个什么意思呢？
    就是在我们访问那个View的时候它不存在。为什么会出现这种情况呢？例如不小心使用了错误的Id,或者访问的时候那个view还不存在。
    使用了错误Id这个估计大家都有此类经历，但是访问时候那个view不存在怎么理解呢？例如我们在手机横屏和竖屏的时候分别使用一套XML layout文件，
    假设横屏中包含了一个竖屏中没有的view,那么在屏幕从横屏旋转到竖屏的时候，NullPointer问题就出现了。
     而ViewBinding中， 产生的binding类中的属性是依据XML layout文件生成的，所以Id不会错。
         */
    }
}