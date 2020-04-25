package com.stone.stoneviewskt

import android.os.Bundle
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.ui.clock.ClockFragment
import com.stone.stoneviewskt.ui.gray.BaseGrayActivity
import com.stone.stoneviewskt.ui.gray.GrayFragment
import com.stone.stoneviewskt.ui.progressbar.CircleProgressbarFragment
import com.stone.stoneviewskt.ui.progressbar.ComplexProgressBarViewFragment
import com.stone.stoneviewskt.ui.progressbar.ObliqueProgressbarFragment
import com.stone.stoneviewskt.ui.radar.RadarFragment
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import com.stone.stoneviewskt.ui.satellite.SatelliteFragment
import com.stone.stoneviewskt.util.showLong
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_rv.adapter = SampleAdapter(TITLES) { _, title ->
            when (title) {
                "轮盘等分" -> startNewUI(RouletteFragment::class.java)
                "卫星式菜单" -> startNewUI(SatelliteFragment::class.java)
                "雷达扫描旋转" -> startNewUI(RadarFragment::class.java)
                "灰度化" -> startActivity<BaseGrayActivity>(KEY_FRAGMENT to GrayFragment::class.java)
                "仅WebView灰度化(对某些网站可能并没有效果，如腾讯视频)" -> startActivity<BaseActivity>(KEY_FRAGMENT to GrayFragment::class.java)
                "斜线进度条" -> startActivity<BaseActivity>(KEY_FRAGMENT to ObliqueProgressbarFragment::class.java)
                "圆环进度条" -> startActivity<BaseActivity>(KEY_FRAGMENT to CircleProgressbarFragment::class.java)
                "左边横向圆角进度条，右边文本为  \"进度/最大进度\"" -> startActivity<BaseActivity>(KEY_FRAGMENT to ComplexProgressBarViewFragment::class.java)
                "绘制时钟表盘" -> startActivity<BaseActivity>(KEY_FRAGMENT to ClockFragment::class.java)
            }
        }

        showLong(R.string.app_name)
    }

    companion object {
        val TITLES = listOf(
                "轮盘等分",
                "卫星式菜单",
                "雷达扫描旋转",
                "灰度化",
                "仅WebView灰度化(对某些网站可能并没有效果，如腾讯视频)",
                "斜线进度条",
                "圆环进度条",
                "左边横向圆角进度条，右边文本为  \"进度/最大进度\"",
                "绘制时钟表盘"
        )
    }
}
