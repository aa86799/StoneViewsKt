package com.stone.stoneviewskt.ui.progress

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.ui.toast.stoneToast
import kotlinx.android.synthetic.main.dialog_progress_lr.*

/**
 * desc:    图片底部操作layout
 * author:  stone
 * email:   aa86799@163.com
 * time:    2020/11/11 19:59
 *
 * lightProgress: [0,1], 0.5 维持原样
 */
class ProgressLeftRightDialog constructor(context: Context, lightProgress: Float) : Dialog(context, R.style.MyBottomSheetDialog) {

    companion object {
        const val RANGE_VALUE = 50
        const val RANGE_VALUE_MID_FLOAT = 0.5f
    }

    init {
        setCanceledOnTouchOutside(false)

        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_progress_lr, null)
        setContentView(contentView)

        prd_op_light.max = 2 * RANGE_VALUE // 从左到右 [0, 100], 个数101
//        prd_op_light.progress = RANGE_VALUE // 测试，进度 停在中间
        prd_op_light.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//                - 亮度区间：-50—+50， 加上0，个数101
                val p = if (progress < RANGE_VALUE) {
                    -RANGE_VALUE + progress
                } else {
                    RANGE_VALUE - (seekBar.max - progress)
                }
                (seekBar as ProgressSeekBar).setProgressText(p.toString())
//                showFloatLabel("Brightness：$p")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                /*
                 * ui 上是： [-50, 0, 50]，从中间向左 越来越低；从中间向右 越来越高
                 * seekBar 以百分比计算，=> [0, 1]
                 * 最终要求 左[-1,0), 右[0,1]
                 */
                var value = seekBar.progress * 1f / seekBar.max // [0, 1]
                stoneToast(context, "progress=$value")
            }
        })
        val light = lightProgress * prd_op_light.max // [-50, 50]
        prd_op_light.progress = light .toInt()

        show()
    }

    override fun show() {
        val layoutParams = window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
        }
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.attributes = layoutParams
        super.show()
    }
}
