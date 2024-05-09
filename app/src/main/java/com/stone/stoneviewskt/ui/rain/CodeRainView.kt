package com.stone.stoneviewskt.ui.rain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.PaintCompat
import com.stone.stoneviewskt.util.getRandomRGBColor
import java.util.Arrays
import kotlin.concurrent.Volatile
import kotlin.math.floor

/**
 * 代码雨
 */
class CodeRainView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : SurfaceView(context, attrs), Runnable, SurfaceHolder.Callback {

    private var mPaint: TextPaint? = null
    private var mBitmapPaint: TextPaint? = null
    private var surface: Surface? = null
    private var sizeChanged = false
    private var mBitmapCanvas: BitmapCusCanvas? = null

    internal class BitmapCusCanvas(val bitmap: Bitmap) : Canvas(bitmap) {

        fun recycle() {
            if (bitmap.isRecycled) {
                return
            }
            bitmap.recycle()
        }
    }

    init {
        initPaint()
    }

    private fun initPaint() {
        //否则提供给外部纹理绘制
        mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.isAntiAlias = true
        mPaint!!.isDither = true
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.textSize = 20f
        mBitmapPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mBitmapPaint!!.isAntiAlias = true
        mBitmapPaint!!.isDither = true
        PaintCompat.setBlendMode(mPaint!!, BlendModeCompat.PLUS)
    }

    var drawThread: Thread? = null
    var characters = """
        import random
        def generate_random_numbers(count):
            numbers = []
            for _ in range(count):
                number = random.randint(1, 100)
                numbers.append(number)
            return numbers
        def calculate_average(numbers):
            total = sum(numbers)
            count = len(numbers)
            average = total / count
            return average
        # 生成10个随机数
        random_numbers = generate_random_numbers(10)
        print("随机数列表:", random_numbers)
        # 计算平均值
        average = calculate_average(random_numbers)
        print("平均值:", average)
    """.trimMargin().toCharArray()
    var drops: IntArray? = null

    @Volatile
    private var isRunning = false
    private val lockSurface = Any()
    private val matrix: Matrix = Matrix()

    init {
        holder.addCallback(this)
    }

    override fun run() {
        while (true) {
            synchronized(surface!!) {
                try {
                    Thread.sleep(32)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                if (!isRunning || Thread.currentThread().isInterrupted) {
                    synchronized(lockSurface) {
                        if (surface != null && surface!!.isValid) {
                            surface!!.release()
                        }
                        surface = null
                    }
                    return
                }
                var canvas: Canvas? = null
                if (sizeChanged || drops == null) {
                    if (mBitmapCanvas != null) {
                        mBitmapCanvas!!.recycle()
                    }
                    mBitmapCanvas = BitmapCusCanvas(
                        Bitmap.createBitmap(width / 2, height / 2, Bitmap.Config.RGB_565))
                    val columCount = (mBitmapCanvas!!.bitmap.getWidth() / mPaint!!.textSize).toInt()
                    drops = IntArray(columCount)
                    Arrays.fill(drops, 1)
                    sizeChanged = false
                }
                canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    surface!!.lockHardwareCanvas()
                } else {
                    surface!!.lockCanvas(null)
                }
                drawChars(mBitmapCanvas!!, mPaint!!)
                matrix.reset()
                matrix.setScale(2f, 2f)
                canvas.drawBitmap(mBitmapCanvas!!.bitmap, matrix, mBitmapPaint)
                surface!!.unlockCanvasAndPost(canvas)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawThread = Thread(this)
        surface = holder.surface
        isRunning = true
        drawThread!!.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        val drawSurface = surface ?: return
        synchronized(drawSurface) { isRunning = false }
        if (drawThread != null) {
            try {
                drawThread!!.interrupt()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        drawThread = null
    }

    private fun drawChars(canvas: Canvas, paint: Paint) {
        canvas.drawColor(argb(0.1f, 0f, 0f, 0f))
//        paint.setColor(-0xff0100)
        paint.setColor(getRandomRGBColor()) // 雨前景色
        val height = height
        val textSize = paint.textSize
        for (i in drops!!.indices) {
            val index = floor(Math.random() * characters.size).toInt()
            canvas.drawText(characters, index, 1, i * textSize, drops!![i] * textSize, paint)
            if (drops!![i] * textSize > height && Math.random() > 0.975) {
                drops!![i] = 0
            }
            drops!![i]++
        }
    }

    companion object {
        fun argb(alpha: Float, red: Float, green: Float, blue: Float): Int {
            return (alpha * 255.0f + 0.5f).toInt() shl 24 or
                    ((red * 255.0f + 0.5f).toInt() shl 16) or
                    ((green * 255.0f + 0.5f).toInt() shl 8) or (blue * 255.0f + 0.5f).toInt()
        }
    }
}