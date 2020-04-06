package com.stone.stoneviewskt

import android.os.Bundle
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_rv.adapter = SampleAdapter(TITLES) { _, _ ->
            startNewUI(RouletteFragment::class.java)
        }
    }

    companion object {
        val TITLES = listOf("轮盘等分")
    }
}
