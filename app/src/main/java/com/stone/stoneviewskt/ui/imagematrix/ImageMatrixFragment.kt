package com.stone.stoneviewskt.ui.imagematrix

import android.graphics.Matrix
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.activity_image_matrix.*

/**
 * Created by Administrator on 2015/2/1 0001.
 */
class ImageMatrixFragment : BaseFragment() {
    private var mEdWidth = 0
    private var mEdHeight = 0
    private val mImageMatrix = FloatArray(9)
    private val mEts = arrayOfNulls<EditText>(9)

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        activity_image_matrix_imv_gl.post {
            mEdWidth = activity_image_matrix_imv_gl.width / 3
            mEdHeight = activity_image_matrix_imv_gl.height / 3
            addEts()
            initImageMatrix()
        }

        activity_image_matrix_change.setOnClickListener {
            change()
        }

        activity_image_matrix_reset.setOnClickListener {
            reset()
        }
    }

    private fun addEts() {
        for (i in 0..8) {
            val et = EditText(requireContext())
            et.gravity = Gravity.CENTER
            mEts[i] = et
            activity_image_matrix_imv_gl.addView(et, mEdWidth, mEdHeight)
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
        activity_image_matrix_imv.setImageMatrix(matrix)
        activity_image_matrix_imv.invalidate()
    }

    private fun reset() {
        initImageMatrix()
        getImageMatrixArray()
        val matrix = Matrix()
        matrix.setValues(mImageMatrix)
        activity_image_matrix_imv.setImageMatrix(matrix)
        activity_image_matrix_imv.invalidate()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_image_matrix
    }
}