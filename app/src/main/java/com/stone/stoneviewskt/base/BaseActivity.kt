package com.stone.stoneviewskt.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import com.stone.stoneviewskt.R
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
//            window.insetsController?.also {
//                it.hide(WindowInsets.Type.statusBars()) //隐藏状态栏
//                it.hide(WindowInsets.Type.navigationBars()) //隐藏导航栏
//            }
            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//下面的 View.xx 在 api 30 过时
            //深色字体，明亮状态栏; 默认不设置时，为浅色字，深色状态栏
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            //浅色字体 深色状态栏
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        super.onCreate(savedInstanceState)

//        window.setFlags(//全屏设置后，进入应用首个界面，能看到状态移上去的动作。
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = statusBarColor()
            window.navigationBarColor = Color.TRANSPARENT //应用内 导航栏，如 actionBar
        }

        //supportFragmentManager 加载 fragment 到 android.R.id.content
//        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
//            val fragment = cls.newInstance() as Fragment
//            supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
//        }

        //fragmentation 框架式 加载 SupportFragment
        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
            val fragment = cls.newInstance() as SupportFragment
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
        startActivity<BaseActivity>(KEY_FRAGMENT to cls, *params)
    }
}