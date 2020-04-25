package com.stone.stoneviewskt.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.jetbrains.anko.startActivity

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 12:06
 */
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val KEY_FRAGMENT = "KEY_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
            val fragment = cls.newInstance() as Fragment
            supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
        }
    }

    /*
     * inline fun 内联函数；
     * <reified T: Fragment>  具体化泛型，使用 reified 关键字必须是 inline 函数。
     * 可以不使用  inline + reified 来声明.
     */
    inline fun <reified T : Fragment> startNewUI(cls: Class<T>, vararg params: Pair<String, Any?>) {
        startActivity<BaseActivity>(KEY_FRAGMENT to cls, *params)
    }
}