package com.stone.stoneviewskt

import android.content.Intent
import android.os.Bundle
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.data.UserData
import com.stone.stoneviewskt.ui.anim.layout.LayoutAnimFragment
import com.stone.stoneviewskt.ui.audio.AudioRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment2
import com.stone.stoneviewskt.ui.circlelayout.CircleLayoutFragment
import com.stone.stoneviewskt.ui.clock.ClockFragment
import com.stone.stoneviewskt.ui.colormatrix.ColorMainFragment
import com.stone.stoneviewskt.ui.dialog.MainDialogManagerFragment
import com.stone.stoneviewskt.ui.gray.BaseGrayActivity
import com.stone.stoneviewskt.ui.gray.GrayFragment
import com.stone.stoneviewskt.ui.imagecrop.StoneImageCropFragment
import com.stone.stoneviewskt.ui.imagefilter.ImageFilterFragment
import com.stone.stoneviewskt.ui.imagematrix.ImageMatrixFragment
import com.stone.stoneviewskt.ui.jetpack.datastore.DataStoreFragment
import com.stone.stoneviewskt.ui.libjpeg.LibJpegFragment
import com.stone.stoneviewskt.ui.lifecycle.LifecycleFragment
import com.stone.stoneviewskt.ui.longimg.LongImageFragment
import com.stone.stoneviewskt.ui.materialdesign.MDActivity
import com.stone.stoneviewskt.ui.materialdesign.MDMainFragment
import com.stone.stoneviewskt.ui.nfc.NfcFragment
import com.stone.stoneviewskt.ui.nfc.NfcNdefActivity
import com.stone.stoneviewskt.ui.parceldata.ParcelDataFragment
import com.stone.stoneviewskt.ui.progressbar.CircleProgressbarFragment
import com.stone.stoneviewskt.ui.progressbar.ComplexProgressBarViewFragment
import com.stone.stoneviewskt.ui.progressbar.ObliqueProgressbarFragment
import com.stone.stoneviewskt.ui.radar.RadarFragment
import com.stone.stoneviewskt.ui.room.RoomFragment
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import com.stone.stoneviewskt.ui.satellite.SatelliteFragment
import com.stone.stoneviewskt.ui.spinner.SpinnerFragment
import com.stone.stoneviewskt.ui.video.VideoCompressFragment
import com.stone.stoneviewskt.ui.webview.ImageLoadWebViewFragment
import com.stone.stoneviewskt.util.showLong
import com.stone.viewbinding.ViewBindActivity
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
                "Floating_Action_Button + Constraint_Layout(圆形布局)" -> startNewUI(CircleLayoutFragment::class.java)
                "Media_Record" -> startNewUI(MediaRecordFragment::class.java)
                "Media_Record2" -> startNewUI(MediaRecordFragment2::class.java)
                "Audio_Record" -> startNewUI(AudioRecordFragment::class.java)
                "Video_Compress" -> startNewUI(VideoCompressFragment::class.java)
                "Nfc_Fragment" -> startActivity<NfcNdefActivity>(KEY_FRAGMENT to NfcFragment::class.java)
                "Color_Matrix" -> startNewUI(ColorMainFragment::class.java)
                "Image_Matrix" -> startNewUI(ImageMatrixFragment::class.java)
                "长图加载" -> startNewUI(LongImageFragment::class.java)
                "图片滤镜" -> startNewUI(ImageFilterFragment::class.java)
                "图片裁剪" -> startNewUI(StoneImageCropFragment::class.java)
                "View Binding" -> startActivity<ViewBindActivity>()
                "Parcel Data" -> startNewUI(ParcelDataFragment::class.java, "user" to UserData("stone", "123456", true))
                "Data Store" -> startNewUI(DataStoreFragment::class.java)
                "WebView加载图片" -> startNewUI(ImageLoadWebViewFragment::class.java)
                "Layout Animation" -> startNewUI(LayoutAnimFragment::class.java)
                "Lifecycle Observer" -> startNewUI(LifecycleFragment::class.java)
                "Dialog" -> startNewUI(MainDialogManagerFragment::class.java)
                "Room" -> startNewUI(RoomFragment::class.java)
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
                "Floating_Action_Button + Constraint_Layout(圆形布局)",
                "Media_Record",
                "Media_Record2",
                "Audio_Record",
                "Video_Compress",
                "Color_Matrix",
                "Image_Matrix",
                "长图加载",
                "图片滤镜",
                "图片裁剪",
                "View Binding",
                "Parcel Data",
                "Data Store",
                "WebView加载图片",
                "Layout Animation",
                "Lifecycle Observer",
                "Dialog",
                "Room"
        ).mapIndexed { index, s -> "$index.$s" }
    }
}
