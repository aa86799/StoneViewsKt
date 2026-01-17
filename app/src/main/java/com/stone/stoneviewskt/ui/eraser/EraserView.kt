package com.stone.stoneviewskt.ui.eraser

import android.content.Context
import android.graphics.*  
import android.util.AttributeSet  
import android.view.MotionEvent  
import android.view.View  
import androidx.core.content.ContextCompat
import com.stone.stoneviewskt.R


/**  
 * 一个实现了橡皮擦效果的自定义View。  
 *  
 * @param context 上下文  
 * @param attrs 属性集  
 * @param defStyleAttr 默认样式属性  
 */  
class EraserView @JvmOverloads constructor(  
    context: Context,  
    attrs: AttributeSet? = null,  
    defStyleAttr: Int = 0  
) : View(context, attrs, defStyleAttr) {

    // 底图 (被擦出来后显示的图)  
    private var backgroundBitmap: Bitmap  

    // 顶图，即用于承载擦除痕迹的图层  
    private var foregroundBitmap: Bitmap  
    private var foregroundCanvas: Canvas  

    // 绘制路径的画笔，即“橡皮擦”  
    private val eraserPaint = Paint().apply {  
        // *** 核心：设置混合模式为 CLEAR ***  
        // 在目标像素上绘制时，会清除目标像素，使其变为完全透明。  
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        style = Paint.Style.STROKE  
        strokeJoin = Paint.Join.ROUND // 路径连接处为圆角  
        strokeCap = Paint.Cap.ROUND   // 路径起始处为圆形  
        strokeWidth = 80f             // 橡皮擦的宽度  
        isAntiAlias = true            // 抗锯齿  
    }  
    
    // 手指滑动的路径  
    private val path = Path()  

    // 记录上一个触摸点的坐标，用于绘制平滑曲线  
    private var lastX = 0f  
    private var lastY = 0f  

    init {  
        // 1. 从 drawable 加载背景图  
        val drawable = ContextCompat.getDrawable(context, R.mipmap.back_girl) // 替换成你自己的图片资源
        backgroundBitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(backgroundBitmap)  
        drawable.setBounds(0, 0, canvas.width, canvas.height)  
        drawable.draw(canvas)  
        
        // 2. 创建前景图层  
        foregroundBitmap = Bitmap.createBitmap(
            backgroundBitmap.width,
            backgroundBitmap.height,
            Bitmap.Config.ARGB_8888
        )
        foregroundCanvas = Canvas(foregroundBitmap)  
        // 3. 在前景图层上填充一层灰色作为遮罩  
        foregroundCanvas.drawColor(Color.LTGRAY)  
    }  

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {  
        // 根据背景图的大小来确定View的大小  
        setMeasuredDimension(backgroundBitmap.width, backgroundBitmap.height)  
    }  

    override fun onDraw(canvas: Canvas) {  
        super.onDraw(canvas)  

        // 1. 绘制底层图片  
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)  
        
        // 2. 绘制顶层遮罩（已经被“擦除”过的）  
        canvas.drawBitmap(foregroundBitmap, 0f, 0f, null)  
    }  

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x  
        val y = event.y  

        when (event.action) {  
            MotionEvent.ACTION_DOWN -> {  
                path.reset()  
                path.moveTo(x, y)  
                lastX = x  
                lastY = y  
                // 返回true表示消费此事件序列  
                return true  
            }  
            MotionEvent.ACTION_MOVE -> {  
                // 使用二阶贝塞尔曲线，使路径更平滑  
                path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2)  
                lastX = x  
                lastY = y  
                
                // *** 核心：在前景图层上使用“橡皮擦”画笔绘制路径 ***  
                foregroundCanvas.drawPath(path, eraserPaint)  
                
                // 通知View重绘  
                invalidate()  
            }  
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {  
                path.reset()  
            }  
        }  
        return super.onTouchEvent(event)  
    }  
    
    /**  
     * 提供一个重置功能，恢复遮罩层  
     */  
    fun reset() {  
        foregroundCanvas.drawColor(Color.LTGRAY) // 重新填充灰色  
        invalidate() // 刷新视图  
    }  
}  