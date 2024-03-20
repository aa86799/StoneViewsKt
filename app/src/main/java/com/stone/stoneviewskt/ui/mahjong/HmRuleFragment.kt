package com.stone.stoneviewskt.ui.mahjong

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentHmRuleBinding

/**
 * desc   : 花麻计算器
 * author : stone
 * email  : aa86799@163.com
 * time   : 2024/3/20 02:07
 */
class HmRuleFragment : BaseBindFragment<FragmentHmRuleBinding>(R.layout.fragment_hm_rule) {

    private val DEF_BOTTOM_SCORE = 3
    private val DEF_POWER = 1

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.btnPlusScore.setOnClickListener {
            val selfScoreText = mBind.tvSelfScore.text.toString()
            var selfScore = if (selfScoreText.isEmpty()) {
                0
            } else selfScoreText.toInt()
            selfScore++
            mBind.tvSelfScore.text = selfScore.toString()

            mBind.btnResult.performClick()
        }

        mBind.btnMinusScore.setOnClickListener {
            val selfScoreText = mBind.tvSelfScore.text.toString()
            var selfScore = if (selfScoreText.isEmpty()) {
                0
            } else selfScoreText.toInt()
            selfScore--
            if (selfScore < 0) {
                selfScore = 0
            }
            mBind.tvSelfScore.text = selfScore.toString()

            mBind.btnResult.performClick()
        }

        mBind.btnPlus.setOnClickListener {
            val powerText = mBind.tvPower.text.toString()
            var power = if (powerText.isEmpty()) {
                DEF_POWER
            } else powerText.toInt()
            power++
            mBind.tvPower.text = power.toString()

            mBind.btnResult.performClick()
        }

        mBind.btnMinus.setOnClickListener {
            val powerText = mBind.tvPower.text.toString()
            var power = if (powerText.isEmpty()) {
                DEF_POWER
            } else powerText.toInt()
            power--
            if (power < 1) {
                power = 1
            }
            mBind.tvPower.text = power.toString()

            mBind.btnResult.performClick()
        }

        mBind.btnResult.setOnClickListener {
            val scoreText = mBind.etBottomScore.text.toString()
            val score = if (scoreText.isEmpty()) {
                mBind.etBottomScore.setText(DEF_BOTTOM_SCORE.toString())
                DEF_BOTTOM_SCORE
            } else scoreText.toInt()

            val selfScoreText = mBind.tvSelfScore.text.toString()
            val selfScore = if (selfScoreText.isEmpty()) {
                0
            } else selfScoreText.toInt()

            val powerText = mBind.tvPower.text.toString()
            val power = if (powerText.isEmpty()) {
                mBind.tvPower.text = DEF_POWER.toString()
                DEF_POWER
            } else powerText.toInt()

            val result = (score + selfScore) * Math.pow(2.0, power.toDouble())
            mBind.tvResult.text = result.toString()

            val sb = StringBuilder()
            sb.append("($score+$selfScore)*${Math.pow(2.0, power.toDouble())}")
            sb.appendLine()
            sb.append("=$result")
            mBind.tvCalcProcess.text = sb.toString()
        }

        quickQuery()
    }

    private fun quickQuery() {
        val query = """
            "番数，自有花，得分(向上取整)" 速查表
            1番：
                0~2，  10分
                3~7，  20分
                8~12， 30分
                
            2番：
                0~2，  20分
                3~4，  30分
                5~7，  40分
                8~9，  50分
                10~12，60分
                
            3番：
                0，    30分
                1~2，  40分
                3，    50分
                4，    60分
                5，    70分
                6~7，  80分
                8，    90分
                9，    100分
                10，   110分
                11~12，120分
                
            4番：
                0，  50分
                1，  70分
                2，  80分
                3，  100分
                4，  120分
                5，  130分
                6，  150分
                7，  160分
                8，  180分
                9，  200分
                10， 210分
                11， 230分
                12， 240分
        """.trimIndent()
        mBind.tvQuickQuery.text = query
    }
}