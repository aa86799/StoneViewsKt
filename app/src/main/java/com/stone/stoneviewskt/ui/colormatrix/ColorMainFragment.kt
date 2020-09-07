package com.stone.stoneviewskt.ui.colormatrix

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_color_main.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/30 14:30
 */
class ColorMainFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        btnColorPlate.setOnClickListener {
            start(ColorPlateFragment())
//            startActivity<BaseActivity>(BaseActivity.KEY_FRAGMENT to ColorPlateFragment::class.java)
        }

        btnColorMatrix.setOnClickListener {
            start(ColorMatrixFragment())
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_color_main
    }
}