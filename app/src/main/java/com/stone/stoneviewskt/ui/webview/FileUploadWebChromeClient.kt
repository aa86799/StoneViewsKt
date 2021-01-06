package com.stone.stoneviewskt.ui.webview

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.stone.stoneviewskt.base.BaseFragment

open class FileUploadWebChromeClient : WebChromeClient {
    //    var photoPath: String? = null
    private var mWebActivity: Activity? = null
    private var mWebFragment: BaseFragment? = null
    var mImageChoosePop: ImageChoosePop? = null
    var onlyCameraAllowed = false

    var valueCallbacks: ValueCallback<Array<Uri>>? = null
    var valueCallback: ValueCallback<Uri>? = null

    constructor(activity: Activity) {
        mWebActivity = activity
    }

    constructor(fragment: BaseFragment) {
        mWebFragment = fragment
    }

    // The undocumented magic method override
    // Eclipse will swear at you if you try to put @Override here
    // For Android 3.0+
    fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
        openChooserActivity(uploadMsg, null, RESULT_CODE_ICE_CREAM)
    }

    // For Android 3.0+
    fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
        openChooserActivity(uploadMsg, acceptType, RESULT_CODE_ICE_CREAM)
    }

    // For Android 4.1
    fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {
        openChooserActivity(uploadMsg, acceptType, RESULT_CODE_ICE_CREAM)
    }

    // For Android5.0+

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        // 这句话建议屏蔽掉，当多次打开上传时会导致崩溃  但这是 google 官方做法
        // 		if (mFilePathCallback != null) {
        // 			mFilePathCallback.onReceiveValue(null);
        // 		}
        if (checkPermission()) {
            val isPictureType = fileChooserParams.acceptTypes.any { it.contains("png") || it.contains("jpg") || it.contains("jpeg") }
            valueCallbacks = filePathCallback
            if (isPictureType) {
                startActivityForResult(REQUEST_CODE_LOLIPOP, fileChooserParams.acceptTypes?.takeIf { it.isNotEmpty() }?.get(0))
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "*/*"
                if (mWebActivity != null)
                    mWebActivity?.startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_CODE_LOLIPOP)
                else
                    mWebFragment?.startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_CODE_LOLIPOP)
            }
            return true
        }
        return false
    }

    open fun startActivityForResult(requestCode: Int, acceptType: String?) {
        mWebFragment?.activity ?: return
        mImageChoosePop = ImageChoosePop(mWebFragment!!, acceptType)
        mImageChoosePop?.popSelectorView({
            valueCallback?.onReceiveValue(null)
            valueCallback = null

            valueCallbacks?.onReceiveValue(null)
            valueCallbacks = null
        }, onlyCameraAllowed)
    }

    private fun checkPermission(): Boolean {
        val permissionList: ArrayList<String> = arrayListOf()
        permissionList.clear()
        if (ContextCompat.checkSelfPermission(mWebFragment?.activity!!, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(mWebFragment?.activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        // 如果列表为空，就是全部权限都获取了，不用再次获取了。不为空就去申请权限
        return when {
            permissionList.size > 0 -> {
                ActivityCompat.requestPermissions(mWebFragment?.activity!!, permissionList.toTypedArray(), RESULT_CODE_PERMISSION)
                false
            }
            else -> true
        }
    }

    private fun openChooserActivity(uploadFile: ValueCallback<Uri>, acceptType: String?, requestCode: Int) {
        valueCallback = uploadFile
        startActivityForResult(requestCode, acceptType)
    }

    companion object {
        private val TAG = "FileUploadWebChromeClient"

        // 	public static final int FILECHOOSER_REQ_CODE = 0x111;
        const val REQUEST_CODE_LOLIPOP = 1
        const val RESULT_CODE_ICE_CREAM = 2
        const val RESULT_CODE_PERMISSION = 3
    }
}