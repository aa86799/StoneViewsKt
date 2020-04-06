package com.stone.stoneviewskt

import android.os.Bundle
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.ui.radar.RadarFragment
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import com.stone.stoneviewskt.ui.satellite.SatelliteFragment
import com.stone.stoneviewskt.util.showLong
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_rv.adapter = SampleAdapter(TITLES) { _, title ->
            when (title) {
                "轮盘等分" -> startNewUI(RouletteFragment::class.java)
                "卫星式菜单" -> startNewUI(SatelliteFragment::class.java)
                "雷达" -> startNewUI(RadarFragment::class.java)
            }
        }

        showLong(R.string.app_name)
    }

    companion object {
        val TITLES = listOf(
            "轮盘等分",
            "卫星式菜单",
            "雷达"
        )
    }
}
