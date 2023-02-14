package com.stone.stoneviewskt.base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.ui.scan.CodeScanFragment
import org.jetbrains.anko.startActivity

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 12:06
 */
open class BaseActivity : SupportActivity() {

    companion object {
        const val KEY_FRAGMENT = "KEY_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//android 30
            val windowInsetsController = window.decorView.windowInsetsController
            // 系统栏 前景深色
//            windowInsetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            // 系统栏 前景浅色
//            windowInsetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
//
//            // 默认的行为，在 api33已过时了，推荐 BEHAVIOR_DEFAULT。 若是结合hide()，从隐藏栏的屏幕边缘滑动后，会固定显示
//            windowInsetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE
//
//            // 如下 behavior 与 hide() 结合 后，从隐藏栏的屏幕边缘滑动，系统栏会再次显示且会在一段时间后再次自动隐藏
//            // 若状态栏和导航栏都设置了隐藏，那滑动后，两者会同时显示
//            windowInsetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//
//            windowInsetsController?.hide(WindowInsets.Type.statusBars()) // 隐藏状态栏
//            // 隐藏导航栏; 会将屏幕中的类似物理按钮(back, home) 隐藏，需要滑一下才可见，使它们变成了虚拟按键
//            windowInsetsController?.hide(WindowInsets.Type.navigationBars())

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//下面的 View.xx 在 api 30 过时
            //深色字体，明亮状态栏; 默认不设置时，为浅色字，深色状态栏
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            //浅色字体 深色状态栏
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }

        // 从 ViewCompat 获取 controller 实例
//            val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) // 过时

        // 从 WindowCompat 获取 controller 实例
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightNavigationBars = false // true, 则将导航栏的前景色更改为浅色(不同的 behavior 要求的值不一样) api >= 26
        windowInsetsController.isAppearanceLightStatusBars = false // true, 则将状态栏的前景色更改为浅色(不同的 behavior 要求的值不一样)   api >= 23
//        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
//        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        // 与 hide() 结合 后, 从隐藏栏的屏幕边缘滑动，系统栏会再次显示且会在一段时间后再次自动隐藏
//        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // 与 hide() 结合 后, 从隐藏栏的屏幕边缘滑动后，会固定显示；isAppearanceLightStatusBars 设置为 false，状态栏才是浅色
//        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
        // 与 hide() 结合 后, 从隐藏栏的屏幕边缘滑动后，会固定显示；isAppearanceLightStatusBars 设置为 false，状态栏才是浅色
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH

        super.onCreate(savedInstanceState)

//        window.setFlags(//全屏设置后，进入应用首个界面，能看到状态移上去的动作。
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = statusBarColor() // 状态栏背景色
            window.navigationBarColor = statusBarColor() //应用内 导航栏，如 actionBar、底部虚拟按键背景
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = Color.RED // 导航栏 分隔线的 颜色
            }
        }

        //supportFragmentManager 加载 fragment 到 android.R.id.content
//        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
//            val fragment = cls.newInstance() as Fragment
//            supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
//        }

        //fragmentation 框架式 加载 SupportFragment
        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
            val fragment = cls.newInstance() as SupportFragment
            fragment.arguments = this@BaseActivity.intent?.extras
            loadRootFragment(android.R.id.content, fragment)
        }
    }

    //状态栏(网络、信号、电量等状态所在栏)颜色
    open fun statusBarColor() = resources.getColor(R.color.colorPrimary)

    /*
     * inline fun 内联函数；
     * <reified T: Fragment>  具体化泛型，使用 reified 关键字必须是 inline 函数。
     * 可以不使用  inline + reified 来声明.
     */
    inline fun <reified T : Fragment> startNewUI(cls: Class<T>, vararg params: Pair<String, Any?>) {
        startActivity<BaseActivity>(KEY_FRAGMENT to cls,  *params)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            if (it is CodeScanFragment) {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}