package com.stone.stoneviewskt.ui.imagefilter

import android.graphics.BitmapFactory
import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.ui.imagefilter.filters.ImageFilterEngine
import kotlinx.android.synthetic.main.fragment_image_filter.*
import org.jetbrains.anko.support.v4.selector

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/4 11:04
 */
class ImageFilterFragment : BaseFragment() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

//        val bm = BitmapFactory.decodeResource(resources, R.mipmap.back_girl_cmiki)
//        val bm = BitmapFactory.decodeResource(resources, R.mipmap.back_girl_test)
        val bm = BitmapFactory.decodeResource(resources, R.mipmap.screenshot_test)
//        val bm = BitmapFactory.decodeResource(resources, R.mipmap.test_image)
        fragment_image_filter_iv.setImageBitmap(bm) //原图

        fragment_image_filter_btn.setOnClickListener {
            selector(
                "滤镜选择", listOf(
                    "黑白",
                    "黑白反转",
                    "Floyd-Steinberg扩散抖动算法", //宽高w*h，超过 1000*1000，有crash风险
                    "Stucki 扩散抖动算法"  //比上面一个更模糊，支持 w*h 更多
                )
            ) { _, index ->
                when (index) {
                    0 -> {
                        ImageFilterEngine.blackWhiteImage(bm)?.let {
                            fragment_image_filter_trans_iv.setImageBitmap(it)
                        }
                    }

                    1 -> {
                        ImageFilterEngine.blackWhiteReverseImage(bm)?.let {
                            fragment_image_filter_trans_iv.setImageBitmap(it)
                        }
                    }

                    2 -> {
                        ImageFilterEngine.floydImage(bm)?.let {
                            fragment_image_filter_trans_iv.setImageBitmap(it)
                        }
                    }

                    3 -> {
                        ImageFilterEngine.stuckiImage(bm)?.let {
                            fragment_image_filter_trans_iv.setImageBitmap(it)
                        }
                    }

                    else -> return@selector
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_image_filter
    }
}