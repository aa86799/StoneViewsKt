package com.stone.stoneviewskt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.stone.stoneviewskt.adapter.SampleAdapter
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.compose.ComposeMainActivity
import com.stone.stoneviewskt.data.UserData
import com.stone.stoneviewskt.databinding.ActivityMainBinding
import com.stone.stoneviewskt.ui.eraser.EraserFragment
import com.stone.stoneviewskt.recyclerview.RecyclerViewDemoFragment
import com.stone.stoneviewskt.ui.anim.layout.LayoutAnimFragment
import com.stone.stoneviewskt.ui.audio.AudioRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment
import com.stone.stoneviewskt.ui.audio.MediaRecordFragment2
import com.stone.stoneviewskt.ui.circlelayout.CircleLayoutFragment
import com.stone.stoneviewskt.ui.clock.ClockFragment
import com.stone.stoneviewskt.ui.colormatrix.ColorMainFragment
import com.stone.stoneviewskt.ui.contentp.ContentProviderFragment
import com.stone.stoneviewskt.ui.dialog.MainDialogManagerFragment
import com.stone.stoneviewskt.ui.gray.BaseGrayActivity
import com.stone.stoneviewskt.ui.gray.GrayFragment
import com.stone.stoneviewskt.ui.imagecrop.StoneImageCropFragment
import com.stone.stoneviewskt.ui.imagefilter.ImageFilterFragment
import com.stone.stoneviewskt.ui.imagematrix.ImageMatrixFragment
import com.stone.stoneviewskt.ui.jetpack.datastore.DataStoreFragment
import com.stone.stoneviewskt.ui.jetpack.workmanager.WorkManagerFragment
import com.stone.stoneviewskt.ui.jumpfragment.JumpActivity
import com.stone.stoneviewskt.ui.ks.KeyStoreFragment
import com.stone.stoneviewskt.ui.libjpeg.LibJpegFragment
import com.stone.stoneviewskt.ui.lifecycle.LifecycleFragment
import com.stone.stoneviewskt.ui.longimg.LongImageFragment
import com.stone.stoneviewskt.ui.mahjong.HmRuleFragment
import com.stone.stoneviewskt.ui.materialdesign.MDActivity
import com.stone.stoneviewskt.ui.materialdesign.MDMainFragment
import com.stone.stoneviewskt.ui.mina.MinaFragment
import com.stone.stoneviewskt.ui.multimg.MultiImageFragment
import com.stone.stoneviewskt.ui.mvi.easy.MVIEasyFragment
import com.stone.stoneviewskt.ui.mvi.pack.MVIPackFragment
import com.stone.stoneviewskt.ui.myhandler.MyHandlerFragment
import com.stone.stoneviewskt.ui.nfc.NfcFragment
import com.stone.stoneviewskt.ui.nfc.NfcNdefActivity
import com.stone.stoneviewskt.ui.page.PageListFragment2
import com.stone.stoneviewskt.ui.parceldata.ParcelDataFragment
import com.stone.stoneviewskt.ui.progress.ProgressLRFragment
import com.stone.stoneviewskt.ui.progressbar.CircleProgressbarFragment
import com.stone.stoneviewskt.ui.progressbar.ComplexProgressBarViewFragment
import com.stone.stoneviewskt.ui.progressbar.ObliqueProgressbarFragment
import com.stone.stoneviewskt.ui.radar.RadarFragment
import com.stone.stoneviewskt.ui.rain.RainFragment
import com.stone.stoneviewskt.ui.room.RoomFragment
import com.stone.stoneviewskt.ui.roulette.RouletteFragment
import com.stone.stoneviewskt.ui.rvlist.StringListFragment
import com.stone.stoneviewskt.ui.satellite.SatelliteFragment
import com.stone.stoneviewskt.ui.savedstate.SavedStateHandleFragment
import com.stone.stoneviewskt.ui.scan.CodeScanFragment
import com.stone.stoneviewskt.ui.shortcut.AppIconShortcutFragment
import com.stone.stoneviewskt.ui.simulate.SimulateEventFragment
import com.stone.stoneviewskt.ui.spinner.SpinnerFragment
import com.stone.stoneviewskt.ui.successiveclick.SuccessiveClickFragment
import com.stone.stoneviewskt.ui.sys.InstalledAppPackageFragment
import com.stone.stoneviewskt.ui.testscroll.ScrollFragment
import com.stone.stoneviewskt.ui.video.VideoCompressFragment
import com.stone.stoneviewskt.ui.viewtransform.ViewTransformFragment
import com.stone.stoneviewskt.ui.webview.ImageLoadWebViewFragment
import com.stone.stoneviewskt.util.showLong
import com.stone.viewbinding.ViewBindActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    private lateinit var mBind: ActivityMainBinding

//  private val handler by lazy { Handler(Looper.myLooper()!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        EventBus.getDefault().register(this)

        if (!isTaskRoot) { //activity是否是任务栈中的根activity
            if (intent.action == Intent.ACTION_MAIN && intent.hasCategory(Intent.CATEGORY_LAUNCHER)) {
                finish()
                return
            }
        }

        //        startActivity<ComposeActivity>()

        //        setContentView(R.layout.activity_main)
        mBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBind.root)

        mBind.activityMainRv.adapter = SampleAdapter(TITLES) { index, title ->
            when (title.substring("$index.".length)) {
                "二维码扫扫描(华为Scan) [23/2/14]" -> startNewUI(CodeScanFragment::class.java)
                "compose [25/10/17]" -> startActivity<ComposeMainActivity>()
                "上下图层模拟擦除效果 [25/10/08]" -> startNewUI(EraserFragment::class.java)
                "代码雨 [24/5/19]" -> startNewUI(RainFragment::class.java)
                "SavedStateHandle 保存和恢复 UI 状态 [24/3/28]" -> startNewUI(SavedStateHandleFragment::class.java)
//                "SavedStateHandle 保存和恢复 UI 状态 [24/3/28]" -> startActivity<SsActivity>()
                "apache mina (socket connect) [24/0227]" -> startNewUI(MinaFragment::class.java)
//                "http server" -> startNewUI(xx::class.java)
                "Android Keystore [24/2/7]" -> startNewUI(KeyStoreFragment::class.java)
                "RecyclerView系列中的ListAdapter(示例) [23/6/16]" -> startNewUI(StringListFragment::class.java)
                "Paging3分页+ConcatAdapter+空数据视图+下拉刷新(SwipeRefreshLayout)+加载更多+错误重试 [23/6/10]" -> startNewUI(PageListFragment2::class.java)
                "长按桌面图标弹出快捷菜单 [23/6/8]" -> startNewUI(AppIconShortcutFragment::class.java)
                "View通过平移、旋转、缩放后，顶点映射 [23/4/3]" -> startNewUI(ViewTransformFragment::class.java)
                "InstalledAppPackage [22/12/30]" -> startNewUI(InstalledAppPackageFragment::class.java)
                "ContentProvider相关 [22/12/11]" -> startNewUI(ContentProviderFragment::class.java)
                "RecyclerView 多种 ViewType 示例 [22/12/24]" -> startNewUI(RecyclerViewDemoFragment::class.java)
                "防连续点击 [22/12/2]" -> startNewUI(SuccessiveClickFragment::class.java)
                "用FragmentManager实现fragment跳转 [22/5/19]" -> startActivity<JumpActivity>()
                "自定义handler [22/3/27]" -> startNewUI(MyHandlerFragment::class.java)
                "改变屏幕亮度 [22/3/16]" -> { // 亮度范围 [0,1]
                    val att = window.attributes
                    if (att.screenBrightness < 1f) {
                        att.screenBrightness = 1f
                        mBind.activityMainRv.keepScreenOn = true // 屏幕常亮，阻止自动息屏
                    } else { // 还原默认亮度策略
                        att.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
                        mBind.activityMainRv.keepScreenOn = false
                    }
                    window.attributes = att
                }
                "左右进度 [22/2/22]" -> startNewUI(ProgressLRFragment::class.java)
                "滚动测试 [21/12/7]" -> startNewUI(ScrollFragment::class.java)
                "轮盘等分 [20/4/4]" -> startNewUI(RouletteFragment::class.java)
                "卫星式菜单 [20/4/6]" -> startNewUI(SatelliteFragment::class.java)
                "雷达扫描旋转 [20/4/6]" -> startNewUI(RadarFragment::class.java)
                "灰度化 [20/4/6]" -> startActivity<BaseGrayActivity>(KEY_FRAGMENT to GrayFragment::class.java)
                "仅WebView灰度化(对某些网站可能并没有效果，如腾讯视频) [20/4/6]" -> startNewUI(GrayFragment::class.java)
                "斜线进度条 [20/4/6]" -> startNewUI(ObliqueProgressbarFragment::class.java)
                "圆环进度条 [20/4/11]" -> startNewUI(CircleProgressbarFragment::class.java)
                "左边横向圆角进度条，右边文本为  \"进度/最大进度\" [20/4/11]" -> startNewUI(ComplexProgressBarViewFragment::class.java)
                "绘制时钟表盘 [20/4/15]" -> startNewUI(ClockFragment::class.java)
                "材料设计 [20/4/26]" -> startActivity<MDActivity>(KEY_FRAGMENT to MDMainFragment::class.java)
                "Spinner [20/4/28]" -> startNewUI(SpinnerFragment::class.java)
                "NDK: lib jpeg [20/7/26]" -> startNewUI(LibJpegFragment::class.java)
                "Floating_Action_Button + Constraint_Layout(圆形布局) [20/7/26]" -> startNewUI(CircleLayoutFragment::class.java)
                "Media_Record [20/7/28]" -> startNewUI(MediaRecordFragment::class.java)
                "Media_Record2 [20/7/28]" -> startNewUI(MediaRecordFragment2::class.java)
                "Audio_Record [20/7/28]" -> startNewUI(AudioRecordFragment::class.java)
                "Video_Compress [20/8/2]" -> startNewUI(VideoCompressFragment::class.java)
                "Nfc_Fragment [20/8/23]" -> startActivity<NfcNdefActivity>(KEY_FRAGMENT to NfcFragment::class.java)
                "Color_Matrix [20/8/30]" -> startNewUI(ColorMainFragment::class.java)
                "Image_Matrix [20/11/9]" -> startNewUI(ImageMatrixFragment::class.java)
                "长图加载 [20/11/30]" -> startNewUI(LongImageFragment::class.java)
                "图片滤镜 [20/12/4]" -> startNewUI(ImageFilterFragment::class.java)
                "图片裁剪 [20/12/6]" -> startNewUI(StoneImageCropFragment::class.java)
                "View Binding [20/12/26]" -> startActivity<ViewBindActivity>()
                "Parcel Data [20/12/26]" -> startNewUI(ParcelDataFragment::class.java, "user" to UserData("stone", "123456", true))
                "Data Store [20/12/27]" -> startNewUI(DataStoreFragment::class.java)
                "WebView加载图片 [21/1/6]" -> startNewUI(ImageLoadWebViewFragment::class.java)
                "Layout Animation [21/1/11]" -> startNewUI(LayoutAnimFragment::class.java)
                "Lifecycle Observer [21/1/13]" -> startNewUI(LifecycleFragment::class.java)
                "Dialog [21/1/24]" -> startNewUI(MainDialogManagerFragment::class.java)
                "Room [21/4/4]" -> startNewUI(RoomFragment::class.java)
                "WorkManager [20/12/27]" -> startNewUI(WorkManagerFragment::class.java)
                "从系统加载多张图片 [21/9/9]" -> startNewUI(MultiImageFragment::class.java)
                "模拟点击 [22/10/27]" -> startNewUI(SimulateEventFragment::class.java)
                "MVI架构示例 [22/11/6]" -> startNewUI(MVIEasyFragment::class.java)
                "MVI架构示例2：模拟分页加载 [22/12/17]" -> startNewUI(MVIPackFragment::class.java)

                "花麻计算器" -> startNewUI(HmRuleFragment::class.java)
            }
        }

        showLong(R.string.app_name)
    }

    companion object {
        val TITLES = listOf(
            "二维码扫扫描(华为Scan) [23/2/14]",
            "compose [25/10/17]",
            "上下图层模拟擦除效果 [25/10/08]",
            "代码雨 [24/5/19]",
            "SavedStateHandle 保存和恢复 UI 状态 [24/3/28]",
            "apache mina (socket connect) [24/0227]",
//            "http server",
            "Android Keystore [24/2/7]",
            "RecyclerView系列中的ListAdapter(示例) [23/6/16]",
            "Paging3分页+ConcatAdapter+空数据视图+下拉刷新(SwipeRefreshLayout)+加载更多+错误重试 [23/6/10]",
            "长按桌面图标弹出快捷菜单 [23/6/8]",
            "View通过平移、旋转、缩放后，顶点映射 [23/4/3]",
            "InstalledAppPackage [22/12/30]",
            "ContentProvider相关 [22/12/11]",
            "RecyclerView 多种 ViewType 示例 [22/12/24]",
            "防连续点击 [22/12/2]",
            "用FragmentManager实现fragment跳转 [22/5/19]",
            "自定义handler [22/3/27]",
            "改变屏幕亮度 [22/3/16]",
            "左右进度 [22/2/22]",
            "滚动测试 [21/12/7]",
            "轮盘等分 [20/4/4]",
            "卫星式菜单 [20/4/6]",
            "雷达扫描旋转 [20/4/6]",
            "灰度化 [20/4/6]",
            "仅WebView灰度化(对某些网站可能并没有效果，如腾讯视频) [20/4/6]",
            "斜线进度条 [20/4/6]",
            "圆环进度条 [20/4/11]",
            "左边横向圆角进度条，右边文本为  \"进度/最大进度\" [20/4/11]",
            "绘制时钟表盘 [20/4/15]",
            "材料设计 [20/4/26]",
            "Spinner [20/4/28]",
            "NDK: lib jpeg [20/7/26]",
            "Floating_Action_Button + Constraint_Layout(圆形布局) [20/7/26]",
            "Media_Record [20/7/28]",
            "Media_Record2 [20/7/28]",
            "Audio_Record [20/7/28]",
            "Video_Compress [20/8/2]",
            "Color_Matrix [20/8/30]",
            "Image_Matrix [20/11/9]",
            "长图加载 [20/11/30]",
            "图片滤镜 [20/12/4]",
            "图片裁剪 [20/12/6]",
            "View Binding [20/12/26]",
            "Parcel Data [20/12/26]",
            "Data Store [20/12/27]",
            "WebView加载图片 [21/1/6]",
            "Layout Animation [21/1/11]",
            "Lifecycle Observer [21/1/13]",
            "Dialog [21/1/24]",
            "Room [21/4/4]",
            "WorkManager [20/12/27]",
            "从系统加载多张图片 [21/9/9]",
            "模拟点击 [22/10/27]",
            "MVI架构示例 [22/11/6]",
            "MVI架构示例2：模拟分页加载 [22/12/17]",

            "花麻计算器"
        ).mapIndexed { index, s -> "$index.$s" }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    fun onEvent() {
        Log.i("TAG", "onEvent: ")
    }

    override fun onDestroy() {
        //        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
