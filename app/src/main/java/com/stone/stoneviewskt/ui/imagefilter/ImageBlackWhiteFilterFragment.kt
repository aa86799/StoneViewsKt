package com.stone.stoneviewskt.ui.imagefilter

import android.graphics.BitmapFactory
import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_image_black_white_filter.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:04
 */
class ImageBlackWhiteFilterFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val bm = BitmapFactory.decodeResource(resources, R.mipmap.printer_test)
        fragment_image_filter_iv.setImageBitmap(bm)

        ImageFilterEngine.blackWhiteImage(bm)?.let {
//        ImageFilterEngine.blackWhiteReverseImage(bm)?.let {
            fragment_image_filter_trans_iv.setImageBitmap(it)
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_image_black_white_filter
    }
}