package com.stone.stoneviewskt.ui.gray

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * desc:    对 url页面，动态 注入 灰度化的 css 脚本
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 20:27
 */
class InjectGrayWebView: WebView {
    var mScope: CoroutineScope? = null

    companion object {
        var mDefaultEncoding = "UTF-8"
    }

    constructor(context: Context) : super(context) {
        initParams(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initParams(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initParams(context, attrs, defStyleAttr)
    }

    private fun initParams(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

    }

    override fun loadUrl(url: String?) {
        mScope ?: return super.loadUrl(url)

        mScope?.launch {
            val resPageData = withContext(Dispatchers.IO) {
                val page: String = getRawString(url)
                val css = "<style type=\"text/css\">html {filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);-webkit-filter: grayscale(100%);}</style>"
                //下面是网页中常用的，各种浏览器内核的灰度设置
//                val css = """
//                    <style type="text/css">
//                    body *{
//                        -webkit-filter: grayscale(100%); /* webkit */
//                        -moz-filter: grayscale(100%); /*firefox*/
//                        -ms-filter: grayscale(100%); /*ie9*/
//                        -o-filter: grayscale(100%); /*opera*/
//                        filter: grayscale(100%);
//                        filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
//                        filter:gray; /*ie9- */
//                        _-webkit-filter: none;
//                        _: none;
//                    }
//                    </style>
//                """
                injectCss(page, css)
            }
            super.loadDataWithBaseURL(url, resPageData, null, mDefaultEncoding, null)
        }
    }

    override fun loadUrl(url: String?, additionalHttpHeaders: MutableMap<String, String>?) {
        super.loadUrl(url, additionalHttpHeaders)
    }

    private fun injectCss(page: String, css: String): String? {
        val headEnd = page.indexOf("</head>")
        var res = ""
        res = if (headEnd > 0) {
            page.substring(0, headEnd) + css + page.substring(headEnd, page.length)
        } else {
            "<head>$css</head>$page"
        }
        return res
    }

    private fun getRawString(webUrl: String?): String {
        val total = StringBuilder()
        try {
            val url = URL(webUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val `is`: InputStream = connection.inputStream
            var encoding: String? = connection.contentEncoding
            if (encoding == null) {
                encoding = mDefaultEncoding
            }
            val reader = BufferedReader(InputStreamReader(`is`, encoding))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                total.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return total.toString()
    }
}