package com.stone.stoneviewskt.ui.gray

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gray.*

/**
 * desc:    使用了灰度化的 WebView
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 19:45
 */
class GrayFragment : BaseFragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onPreparedView(savedInstanceState: Bundle?) {
        val injectWebView = fragment_gray_wv
        injectWebView.mScope = lifecycleScope
        //设置WebView属性，能够执行Javascript脚本
        val webSettings = fragment_gray_wv.settings
        webSettings.javaScriptEnabled = true
        //设置可以访问文件
        webSettings.allowFileAccess = true
        //设置支持缩放
        webSettings.builtInZoomControls = true
        //加载需要显示的网页
//        fragment_gray_wv.loadUrl("https://www.baidu.com")
        fragment_gray_wv.loadUrl("https://v.qq.com/")
        //设置Web视图
        fragment_gray_wv.webViewClient = WebViewClient()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_gray
    }
}