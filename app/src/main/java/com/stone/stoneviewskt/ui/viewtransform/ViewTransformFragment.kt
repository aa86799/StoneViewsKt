package com.stone.stoneviewskt.ui.viewtransform

import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentViewTransformBinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:    对View 进行旋转、缩放、平移的属性变换后，通过 matrix，获取外矩形顶点
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/4/3 8:11
 */
class ViewTransformFragment: BaseBindFragment<FragmentViewTransformBinding>(R.layout.fragment_view_transform) {

    private lateinit var mDrawerView: DrawerView

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        mDrawerView = view?.findViewById(R.id.drawer_view)!!

        var tx = 0f
        var ty = 0f
        var rx = 0f
        var ry = 0f
        var sx = 1f
        var sy = 1f
        mBind.btnAction.setOnClickListener {
            val view = mBind.ivImg
//            view.pivotX = view.left + view.width/2f
//            view.pivotY = view.top + view.height/2f

            selfProperties(view)
            tx += 0f
            ty -= 5f
            rx += 20f
            ry += 20f
            sx += 0.03f
            sy += 0.03f
            selfTransform(view, tx, ty, rx, ry, sx, sy)

//            val map = mapPoints(view)
//            logi("变换后的 ltrb: ${map[0]}, ${map[1]}, ${map[2]}, ${map[3]}")
//            logi("----------------")
//            //todo 基于 变换后的 ltrb，加一个自定义 view layout  有最外层 view那么大； 当 view 变换后，ltrb 在 layout内部时，在 layout 上以 ltrb为圆心 画出一个半径为10的小圆
//            selfProperties(view)
//            mDrawerView.drawPoints(listOf(PointF(map[0], map[1]), PointF(map[2], map[1]), PointF(map[0], map[3]), PointF(map[2], map[3])))

            val dst = RectF()
            val src = RectF(view.left.toFloat(), view.top.toFloat(), view.right.toFloat(), view.bottom.toFloat())
            mapRect(view, dst, src)
            logi("变换后的: ${dst}")
            logi("----------------")
            selfProperties(view)
            mDrawerView.drawPoints(listOf(PointF(dst.left, dst.top), PointF(dst.right, dst.top), PointF(dst.left, dst.bottom), PointF(dst.right, dst.bottom)))
        }
    }

    // View 通过如下操作，在平移、旋转、缩放后，其本身的 left、top、right、bottom、width、height是没有变化的
    private fun selfTransform(view: View, tx: Float, ty: Float, rx: Float, ry: Float, sx: Float, sy: Float) {
        view.translationX = tx
        view.translationY = ty

        val px = view.pivotX
        val py = view.pivotY
        view.pivotX = view.left + view.width/2f
        view.pivotY = view.top + view.height/2f
//        view.rotationX = rx
//        view.rotationY = ry
        view.rotation = ry
        view.scaleX = sx
        view.scaleY = sy
        view.pivotX = px
        view.pivotY = py
    }

    private fun selfProperties(view: View) {
        logi("self-ltrb: ${view.left}, ${view.top}, ${view.right}, ${view.bottom}")
        logi("tx, ty: ${view.translationX}, ${view.translationY}")
        logi("rx, ry: ${view.rotationX}, ${view.rotationY}； rotation: ${view.rotation}")
        logi("sx, sy: ${view.scaleX}, ${view.scaleY}")
        logi("----------------")
    }

    fun mapPoints(model: View): FloatArray {
        val l = model.left.toFloat()
        val t = model.top.toFloat()
        val r = model.left + model.width.toFloat()
        val b = model.top + model.height.toFloat()
        val dst = floatArrayOf(l, t, r, b) // 只传两个点，就含有 ltrb 坐标
        val matrix = Matrix()
        val cx = l + (r - l) / 2f
        val cy = t + (b - t) / 2f
        matrix.postScale(model.scaleX, model.scaleY, cx, cy)
        matrix.postRotate(model.rotation, cx, cy) // 以view的中心点旋转
        matrix.postTranslate(model.translationX, model.translationY)

        matrix.mapPoints(dst)
        return dst

        /*
    * 1. 仅 trranslate，再mapPoints，没问题
    * 2. rotation 是 绕一点 做平面的旋转
    * 3. rotationX  绕 x 轴上一点 做翻转;  rotationY 绕 y 轴上一点 做翻转
    * 4. rotation  rotationX/Y  scaleX/Y  都会基于 pivotX/Y
    * 5. 若要绕 view 的 中心点， 进行 rotate scale 变换：先设置 pivotX/Y ，rotate/scale 后，要将 pivot 再重置回原来的，
    * 6. 单独缩放(或加平移 没啥问题)
    * 7. 单独旋转，左上和右下角 没问题； 右上和左下 随着角度的变化 就越不正确了？？
    */

        /*
            matrix.postScale(model.scaleX, model.scaleY, cx, cy)
            matrix.postTranslate(model.translationX, model.translationY)
            对应
            view.translationX = tx
            view.translationY = ty
            view.scaleX = sx
            view.scaleY = sy
            结果是正常的
            但加上  rotation  就不对了？
         */
    }

    fun mapRect(model: View, dst: RectF, src: RectF) {
        val l = model.left.toFloat()
        val t = model.top.toFloat()
        val r = model.left + model.width.toFloat()
        val b = model.top + model.height.toFloat()
        val matrix = Matrix()
        val cx = l + (r - l) / 2f
        val cy = t + (b - t) / 2f
        matrix.postScale(model.scaleX, model.scaleY, cx, cy)
        matrix.postRotate(model.rotation, cx, cy) // 以view的中心点旋转
        matrix.postTranslate(model.translationX, model.translationY)
        matrix.mapRect(dst, src)
        /*
         * 旋转 用 mapPoints() 会有问题；具体怎么解决，暂时还没法子；但用 mapRect() 就正常了
         *
         * 当变换顺序是  translation(T)、rotate(R)、scale(S)；
         * matrix 中的顺序  用   RST 或 SRT 都会得到正确的结果；但若用 T在最前，就会得出错误结果
         *
         * postXxx()， 当前矩阵在 后/右;
         *      post->TRS ==> S(R(T))
         *      post->TSR ==> R(S(T))
         *
         *      post->RST ==> T(S(R))
         *      post->SRT ==> T(R(S))
         */
    }

}