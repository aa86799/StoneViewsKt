package com.stone.stoneviewskt.base

import android.app.Activity
import android.app.AppOpsManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.collection.arrayMapOf
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.children
import androidx.core.view.contains
import com.stone.stoneviewskt.BuildConfig
import com.stone.stoneviewskt.MainActivity
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.ui.floatview.FloatConcreteFragment
import com.stone.stoneviewskt.ui.floatview.GodFloatViewWithWindowManager
import com.stone.stoneviewskt.util.dp

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/2/5 12:22
 */
class FloatActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var floatView: GodFloatViewWithWindowManager? = null
    private val ivWebMap = arrayMapOf<Int, ImageView>() // web 小图标

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        debugMode(activity)
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        ivWebMap.remove(activity.javaClass.hashCode())

        if (activity is MainActivity) {
            floatView?.let {
                activity.windowManager.removeView(it)
            }
            floatView = null
        }
    }

    // 递归判断，指定 viewGroup 的子view中是否含有 WebView
    private fun hasWebView(viewGroup: ViewGroup?): Boolean {
        viewGroup?.children?.forEach {
            if (it is WebView) {
                return true
            }
            if (it is ViewGroup) {
                if (hasWebView(it)) return true
            }
        }
        return false
    }

    // 开发调试模式
    private fun debugMode(activity: Activity) {
        if (!BuildConfig.DEBUG) return
        val content = activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)

        if (hasWebView(content)) {
            var ivWeb = ivWebMap[activity.javaClass.hashCode()]
            if (ivWeb == null) {
                ivWeb = ImageView(activity)
                ivWeb.setImageResource(R.mipmap.web)
                ivWebMap[activity.javaClass.hashCode()] = ivWeb
            }
            if (!content.contains(ivWeb)) {
                val lp = FrameLayout.LayoutParams(50.dp, 25.dp)
                content.addView(ivWeb, lp)
            }
        }

        // 判断县浮窗 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appOpsMgr = activity.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
            val mode = appOpsMgr?.checkOpNoThrow(
                AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Process.myUid(), activity.packageName
            )
            if (mode != AppOpsManager.MODE_ALLOWED && mode != AppOpsManager.MODE_IGNORED) {
                requestPermission(activity)
                return
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
                requestPermission(activity)
                return
            }
        }

        // 已经有权限，直接显示悬浮窗
        if (floatView == null) {
            floatView = GodFloatViewWithWindowManager(activity)
            floatView?.addToWindow(activity.window)
            floatView?.setClick {
                startActivity(activity, Intent(activity, BaseActivity::class.java), Bundle().apply {
                    putString(BaseActivity.KEY_FRAGMENT, FloatConcreteFragment::class.java.canonicalName)
                })
            }
        }
    }

    private fun requestPermission(activity: Activity) {
        // 没有权限，须要申请权限，由于是打开一个授权页面，因此拿不到返回状态的，因此建议是在onResume方法中重新执行一次校验
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
//            intent.data = Uri.parse("package:" + activity.packageName)
        intent.data = Uri.fromParts("package", activity.packageName, null)
        startActivity(activity, intent, null)
    }
}