package com.stone.stoneviewskt.ui.audio

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.util.logi
import java.util.*
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * 语音录制的动画效果
 * Created by chenxf on 17-6-12.
 */
class LineWaveVoiceView : View {
    private var paint: Paint = Paint()

    //矩形波纹颜色
    private var lineColor = 0

    //矩形波纹宽度
    private var lineWidth = 0f
    private var textSize = 0f
    private var text = DEFAULT_TEXT
    private var textColor = 0
    private var isStart = false
    private val LINE_W = 10 //默认矩形波纹的宽度
    private val MIN_WAVE_H = 15 //最小的矩形线高，是线宽的2倍，线宽从 lineWidth 获得
    private var MAX_WAVE_H = 100 //最高波峰

    //默认矩形波纹的高度，总共10个矩形，左右各有15个
    private val DEFAULT_WAVE_HEIGHT = intArrayOf(MIN_WAVE_H, MIN_WAVE_H * 2, MIN_WAVE_H * 4,
            MIN_WAVE_H * 3, MIN_WAVE_H * 2, MIN_WAVE_H * 4, MIN_WAVE_H * 3, MIN_WAVE_H * 2, MIN_WAVE_H, MIN_WAVE_H * 2,
            MIN_WAVE_H * 2, MIN_WAVE_H, MIN_WAVE_H * 2,MIN_WAVE_H * 2, MIN_WAVE_H)
    private val mWaveList = LinkedList<Int>()
    private val rectRight = RectF() //右边波纹矩形的数据，10个矩形复用一个rectF
    private val rectLeft = RectF() //左边波纹矩形的数据
    var mList = LinkedList<Int>()

    constructor(context: Context) : this(context, null, 0)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        resetList(mList, DEFAULT_WAVE_HEIGHT)

        lineColor = resources.getColor(R.color.colorAccent)
        lineWidth = LINE_W.toFloat()
        textSize = 42f
        textColor = Color.parseColor("#666666")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2
        val centerY = height / 2

        //更新时间
        paint.strokeWidth = 0f
        paint.color = textColor
        paint.textSize = textSize
        val textWidth = paint.measureText(text)
        canvas.drawText(text, centerX - textWidth / 2, centerY - (paint.ascent() + paint.descent()) / 2, paint)

        val wOffset = max(((centerX - textWidth/2) / DEFAULT_WAVE_HEIGHT.size).toInt(), LINE_W)

        //更新左右两边的波纹矩形
        paint.color = lineColor
        paint.style = Paint.Style.FILL
        paint.strokeWidth = lineWidth
        paint.isAntiAlias = true
        for (i in DEFAULT_WAVE_HEIGHT.indices) {
            //右边矩形
            rectRight.left = centerX + i * wOffset + textWidth / 2 + lineWidth
            rectRight.top = (centerY - mList[i]).toFloat()
            rectRight.right = centerX + i * wOffset + textWidth / 2 + lineWidth * 2
            rectRight.bottom = (centerY + mList[i]).toFloat()

            //左边矩形
            rectLeft.left = centerX - (i * wOffset + textWidth / 2 + 2 * lineWidth)
            rectLeft.top = (centerY - mList[i]).toFloat()
            rectLeft.right = centerX - (i * wOffset + textWidth / 2 + lineWidth)
            rectLeft.bottom = (centerY + mList[i]).toFloat()
            logi("l-rect:$rectLeft")
            logi("r-rect:$rectRight")
            canvas.drawRoundRect(rectRight, 6f, 6f, paint)
            canvas.drawRoundRect(rectLeft, 6f, 6f, paint)
        }
    }

    fun refreshElement(maxAmp: Float) {
        val waveH = MIN_WAVE_H + (maxAmp * (MAX_WAVE_H - Random.nextInt(MIN_WAVE_H))).roundToInt()
        logi("wavh:$waveH")
        mList.add(0, waveH)
        mList.removeLast()
        postInvalidate()
    }

    fun setText(text: String) {
        this.text = text
        postInvalidate()
    }

    fun reset() {
        mWaveList.clear()
        resetList(mList, DEFAULT_WAVE_HEIGHT)
        text = DEFAULT_TEXT
        postInvalidate()
    }

    private fun resetList(list: MutableList<Int>, array: IntArray) {
        list.clear()
        for (i in array.indices) {
            list.add(array[i])
        }
    }

    companion object {
        private val TAG = LineWaveVoiceView::class.java.simpleName
        private const val DEFAULT_TEXT = " 倒计时 3:00 "
        private const val UPDATE_INTERVAL_TIME = 100 //100ms更新一次
    }
}