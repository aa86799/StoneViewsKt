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
    }
}