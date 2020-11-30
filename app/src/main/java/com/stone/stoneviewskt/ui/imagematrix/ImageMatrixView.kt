package com.stone.stoneviewskt.ui.imagematrix

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import com.stone.stoneviewskt.R

/**
 * 图片绘制用到 Matrix .
 *  drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint)
 */
class ImageMatrixView : View {
    private var mBitmap: Bitmap? = null
    private var mMatrix: Matrix? = null

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.satellite_button_normal)
        setImageMatrix(Matrix())
    }

    fun setImageMatrix(matrix: Matrix?) {
        mMatrix = matrix
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
            mMatrix?.let { m ->
                canvas.drawBitmap(it, m, null)
            }
        }
    }
}