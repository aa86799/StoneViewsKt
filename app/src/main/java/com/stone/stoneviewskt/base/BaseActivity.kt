package com.stone.stoneviewskt.base

import android.os.Bundle
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
        super.onCreate(savedInstanceState)

        (intent?.getSerializableExtra(KEY_FRAGMENT) as? Class<*>)?.let { cls ->
            val fragment = cls.newInstance() as Fragment
            supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
        }
    }

    fun <T> startNewUI(cls: Class<T>, vararg params: Pair<String, Any?>) {
        startActivity<BaseActivity>(KEY_FRAGMENT to cls, *params)
    }
}