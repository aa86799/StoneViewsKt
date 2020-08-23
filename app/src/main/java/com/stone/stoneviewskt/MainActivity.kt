package com.stone.stoneviewskt

import android.content.Intent
import android.os.Bundle
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.ui.audio.AudioRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment2
import com.stone.stoneviewskt.ui.circlelayout.CircleLayoutFragment
import com.stone.stoneviewskt.ui.clock.ClockFragment
import com.stone.stoneviewskt.ui.gray.BaseGrayActivity
import com.stone.stoneviewskt.ui.gray.GrayFragment
import com.stone.stoneviewskt.ui.libjpeg.LibJpegFragment
import com.stone.stoneviewskt.ui.materialdesign.MDActivity
import com.stone.stoneviewskt.ui.materialdesign.MDMainFragment
import com.stone.stoneviewskt.ui.progressbar.CircleProgressbarFragment
import com.stone.stoneviewskt.ui.progressbar.ComplexProgressBarViewFragment
import com.stone.stoneviewskt.ui.progressbar.ObliqueProgressbarFragment
import com.stone.stoneviewskt.ui.radar.RadarFragment
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import com.stone.stoneviewskt.ui.satellite.SatelliteFragment
import com.stone.stoneviewskt.ui.spinner.SpinnerFragment
import com.stone.stoneviewskt.ui.video.VideoCompressFragment
import com.stone.stoneviewskt.util.showLong
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTaskRoot) {//activity是否是任务栈中的根activity
            if (intent.action == Intent.ACTION_MAIN && intent.hasCategory(Intent.CATEGORY_LAUNCHER)) {
                finish()
                return
            }
        }

        setContentView(R.layout.activity_main)

        activity_main_rv.adapter = SampleAdapter(TITLES) { index, title ->
            when (title.substring("$index.".length)) {
                "轮盘等分" -> startNewUI(RouletteFragment::class.java)
                "卫星式菜单" -> startNewUI(SatelliteFragment::class.java)
                "雷达扫描旋转" -> startNewUI(RadarFragment::class.java)
                "灰度化" -> startActivity<BaseGrayActivity>(KEY_FRAGMENT to GrayFragment::class.java)
                "仅WebView灰度化(对某些网站可能并没有效果，如腾讯视频)" -> startNewUI(GrayFragment::class.java)
                "斜线进度条" -> startNewUI(ObliqueProgressbarFragment::class.java)
                "圆环进度条" -> startNewUI(CircleProgressbarFragment::class.java)
                "左边横向圆角进度条，右边文本为  \"进度/最大进度\"" -> startNewUI(ComplexProgressBarViewFragment::class.java)
                "绘制时钟表盘" -> startNewUI(ClockFragment::class.java)
                "材料设计" -> startActivity<MDActivity>(KEY_FRAGMENT to MDMainFragment::class.java)
                "Spinner" -> startNewUI(SpinnerFragment::class.java)
                "NDK: lib jpeg" -> startNewUI(LibJpegFragment::class.java)
                "FloatingActionButton + ConstraintLayout(圆形布局)" -> startNewUI(CircleLayoutFragment::class.java)
                "MediaRecord" -> startNewUI(MediaRecordFragment::class.java)
                "MediaRecord2" -> startNewUI(MediaRecordFragment2::class.java)
                "AudioRecord" -> startNewUI(AudioRecordFragment::class.java)
                "VideoCompress" -> startNewUI(VideoCompressFragment::class.java)
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
                "绘制时钟表盘",
                "材料设计",
                "Spinner",
                "NDK: lib jpeg",
                "FloatingActionButton + ConstraintLayout(圆形布局)",
                "MediaRecord",
                "MediaRecord2",
                "AudioRecord",
                "VideoCompress"
        ).mapIndexed { index, s -> "$index.$s" }
    }
}
