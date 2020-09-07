package com.stone.stoneviewskt.ui.colormatrix

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_color_plate.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.sdk27.coroutines.onSeekBarChangeListener

/**
 * desc:    色相、饱和度、亮度；调节.  通过 ColorMatrix，setRotate(), setSaturation(), setScale() 实现。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/30 15:20
 */
class ColorPlateFragment : BaseFragment() {

    private lateinit var mBitmap: Bitmap
    private var mHue = 0f //色相
    private var mSaturation = 0f //饱和度
    private var mLum = 0f //亮度

    companion object {
        private const val MAX_VALUE = 255
        private const val MID_VALUE = 127
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        init()

        seekbarHue.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //-180~180
                seekBar ?: return
                mHue = (progress - MID_VALUE).toFloat() / MID_VALUE * 180
                fragment_color_plate_iv.imageBitmap =
                        ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        seekbarSaturation.onSeekBarChangeListener {
            onProgressChanged { seekBar, progress, _ ->
                seekBar ?: return@onProgressChanged
                //0 ~ 2.0f
                mSaturation = progress.toFloat() / MID_VALUE
                fragment_color_plate_iv.imageBitmap =
                        ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum)
            }

            onStartTrackingTouch {

            }

            onStopTrackingTouch {

            }
        }

        seekbarLum.onSeekBarChangeListener {
            onProgressChanged { seekBar, progress, _ ->
                seekBar ?: return@onProgressChanged
                //0~2.0f
                mLum = progress.toFloat() / MID_VALUE
                fragment_color_plate_iv.imageBitmap =
                        ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum)
            }
        }
    }

    private fun init() {
        seekbarHue.max = MAX_VALUE
        seekbarSaturation.max = MAX_VALUE
        seekbarLum.max = MAX_VALUE

        seekbarHue.progress = MID_VALUE
        seekbarSaturation.progress = MID_VALUE
        seekbarLum.progress = MID_VALUE

        //默认值
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.back_girl)
        mHue = 0f
        mSaturation = 1f
        mLum = 1f
        mBitmap =  ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum)
        fragment_color_plate_iv.setImageBitmap(mBitmap)

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_color_plate
    }
}