package com.stone.stoneviewskt.ui.materialdesign

import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseActivity

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/26 16:07
 */
class MDActivity : BaseActivity() {

    override fun statusBarColor(): Int {
        return resources.getColor(R.color.colorPrimaryLight)
    }
}