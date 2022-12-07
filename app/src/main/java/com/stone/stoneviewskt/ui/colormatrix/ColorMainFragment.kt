package com.stone.stoneviewskt.ui.colormatrix

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentColorMainBinding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/30 14:30
 */
class ColorMainFragment : BaseBindFragment<FragmentColorMainBinding>(R.layout.fragment_color_main) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        //色相、饱和度、亮度；调节 通过 ColorMatrix
        mBind.btnColorPlate.setOnClickListener {
            start(ColorPlateFragment())
//            startActivity<BaseActivity>(BaseActivity.KEY_FRAGMENT to ColorPlateFragment::class.java)
        }

        //ColorMatrix
        mBind.btnImageMatrix.setOnClickListener {
            start(ColorMatrixFragment())
        }
    }

}