package com.stone.stoneviewskt.ui.colormatrix

import android.graphics.*
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_color_matrix.*

/**
 * desc:    ColorMatrix 4x5 float matrix.
 *          paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
 *          canvas.draw(...paint);
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/30 14:30
 */
class ColorMatrixFragment : BaseFragment() {

    private lateinit var mBitmap: Bitmap
    private var mEtWidth = 0
    private var mEtHeight: Int = 0
    private val mEts = mutableListOf<EditText>()
    private val mColorMatrix = FloatArray(20)
    private val mOptions = BitmapFactory.Options()

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        init()

        btnChange.setOnClickListener {
            getMatrix()
            setImageMatrix()
        }

        btnReset.setOnClickListener {
            loadBitmap()
            initMatrix()
            getMatrix()
            setImageMatrix()

        }

        btnNegative.setOnClickListener {
            loadBitmap()
            mBitmap = ImageHelper.handleImageNegative(mBitmap)
            fragment_color_matrix_iv.setImageBitmap(mBitmap)
        }

        btnOldPhoto.setOnClickListener {
            loadBitmap()
            mBitmap = ImageHelper.handleImagePixelsOldPhoto(mBitmap)
            fragment_color_matrix_iv.setImageBitmap(mBitmap)
        }

        btnRelief.setOnClickListener {
            loadBitmap()
            mBitmap = ImageHelper.handleImagePixelsRelief(mBitmap)
            fragment_color_matrix_iv.setImageBitmap(mBitmap)
        }
    }

    private fun init() {
        loadBitmap()
        fragment_color_matrix_group.post {
            mEtWidth = fragment_color_matrix_group.width / 5
            mEtHeight = fragment_color_matrix_group.height / 4
            addEts()
            initMatrix()
        }
    }

    private fun loadBitmap() {
        //后面三个方法，会改变像素点，单纯的 重置矩阵，达不到reset的目的。  所以直接重新 加载 bitmap
//        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.back_girl, mOptions)
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.back_girl)
        mOptions.inBitmap = mBitmap
        fragment_color_matrix_iv.setImageBitmap(mBitmap)
    }

    private fun addEts() {
        for (i in mColorMatrix.indices) {
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED //有符号数字
            mEts.add(editText)
            fragment_color_matrix_group.addView(editText, mEtWidth, mEtHeight)
        }
    }

    private fun initMatrix() {
        for (i in 0 until mEts.size) {
            if (i % 6 == 0) {
                mEts[i].setText(1.toString())
            } else {
                mEts[i].setText(0.toString())
            }
        }
    }

    private fun getMatrix() {
        for (i in mEts.indices) {
            val v = mEts[i].text.toString()
            if (v.isEmpty()) {
                break
            }
            mColorMatrix[i] = mEts[i].text.toString().toFloat()
        }
    }

    private fun setImageMatrix() {
        val bmp = Bitmap.createBitmap(mBitmap.width, mBitmap.height, Bitmap.Config.ARGB_8888)
        val colorMatrix = ColorMatrix()
        colorMatrix.set(mColorMatrix)
        val canvas = Canvas(bmp)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(mBitmap, 0f, 0f, paint)
        fragment_color_matrix_iv.setImageBitmap(bmp)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_color_matrix
    }
}