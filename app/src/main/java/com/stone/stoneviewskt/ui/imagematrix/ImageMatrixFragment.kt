package com.stone.stoneviewskt.ui.imagematrix

import android.graphics.Matrix
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentImageMatrixBinding

/**
 * desc:    Matrix 用于图片操作：旋转、缩放、平移、错切。
 * author:  stone
 * email:   aa86799@163.com
 * time:    2020/11/9
 */
class ImageMatrixFragment : BaseBindFragment<FragmentImageMatrixBinding>(R.layout.fragment_image_matrix) {
    private var mEdWidth = 0
    private var mEdHeight = 0
    private val mImageMatrix = FloatArray(9)
    private val mEts = arrayOfNulls<EditText>(9)

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.imvGl.post {
            mEdWidth = mBind.imvGl.width / 3
            mEdHeight = mBind.imvGl.height / 3
            addEts()
            initImageMatrix()
        }

        mBind.btnChange.setOnClickListener {
            change()
        }

        mBind.btnReset.setOnClickListener {
            reset()
        }
    }

    private fun addEts() {
        for (i in 0..8) {
            val et = EditText(requireContext())
            et.gravity = Gravity.CENTER
            mEts[i] = et
            mBind.imvGl.addView(et, mEdWidth, mEdHeight)
        }
    }

    private fun initImageMatrix() {
        for (i in 0..8) {
            if (i % 4 == 0) {
                mEts[i]!!.setText(1.toString())
            } else {
                mEts[i]!!.setText(0.toString())
            }
        }
    }

    private fun getImageMatrixArray() {
        for (i in 0..8) {
            val ed = mEts[i]
            mImageMatrix[i] = ed!!.text.toString().toFloat()
        }
    }

    private fun change() {
        getImageMatrixArray()
        val matrix = Matrix()
        /*
    public static final int MSCALE_X = 0;   //!< use with getValues/setValues
    public static final int MSKEW_X  = 1;   //!< use with getValues/setValues
    public static final int MTRANS_X = 2;   //!< use with getValues/setValues
    public static final int MSKEW_Y  = 3;   //!< use with getValues/setValues
    public static final int MSCALE_Y = 4;   //!< use with getValues/setValues
    public static final int MTRANS_Y = 5;   //!< use with getValues/setValues
    public static final int MPERSP_0 = 6;   //!< use with getValues/setValues
    public static final int MPERSP_1 = 7;   //!< use with getValues/setValues
    public static final int MPERSP_2 = 8;   //!< use with getValues/setValues
         */
        matrix.setValues(mImageMatrix);
//        matrix.setTranslate(150, 150);
//        matrix.setScale(2f, 2f)
//        matrix.postTranslate(200f, 200f)
        mBind.imv.setImageMatrix(matrix)
        mBind.imv.invalidate()
    }

    private fun reset() {
        initImageMatrix()
        getImageMatrixArray()
        val matrix = Matrix()
        matrix.setValues(mImageMatrix)
        mBind.imv.setImageMatrix(matrix)
        mBind.imv.invalidate()
    }
}