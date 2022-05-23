package com.stone.stoneviewskt.ui.jumpfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.stone.stoneviewskt.R

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/5/19 10:49
 */
class JumpActivity : AppCompatActivity() {

    /*
     Activity#getSupportFragmentManager()

     返回 当前 Fragment 的 parent Fragment，且 parent 是直属于 Activity的， 否则返回null
     Fragment#getParentFragmentManager()

     Fragment#getChildFragmentManager()
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jump_fragment)
        supportFragmentManager.commit {
            add<JumpAFragment>(R.id.activity_jump_fragment_root)
//            replace<JumpAFragment>(R.id.activity_jump_fragment_root)
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}