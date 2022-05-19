package com.stone.stoneviewskt.ui.webview

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.*
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentImageLoadWebBinding
import com.stone.stoneviewskt.util.BitmapUtil
import com.stone.stoneviewskt.util.KitKatUtil
import com.stone.stoneviewskt.util.logi
import org.jetbrains.anko.support.v4.alert
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions
import java.io.File

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/1/6 11:16
 */
@RuntimePermissions
class ImageLoadWebViewFragment: BaseBindFragment<FragmentImageLoadWebBinding>() {

    private lateinit var mFileUploadWebChromeClient: FileUploadWebChromeClient

    companion object {
        private const val REQUEST_CODE_PHOTO = 0x123
    }

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentImageLoadWebBinding {
        return inflateBinding(inflater, container)
    }
    
    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentIlwIv.setOnClickListener {
          val method = "javascript:menuInit()"  //可以
//            val method = "javascript:window.menuInit()" //可以
            mBind.fragmentIlwWv.loadUrl(method)

//            choosePhotoWithPermissionCheck()
        }

        mBind.fragmentIlwWv.setBackgroundColor(0)
//        mBind.fragmentIlwWv.setBackgroundResource(R.drawable.kotlin)

        mFileUploadWebChromeClient = object : FileUploadWebChromeClient(this@ImageLoadWebViewFragment) {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (_mActivity == null || _mActivity.isFinishing) return
            }

            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                logi("$message")
                result?.cancel()
                if (_mActivity.isFinishing) {
                    return true
                }
                alert (message ?: "", "") {
                    negativeButton("取消") {
                        result?.cancel()
                    }
                    positiveButton("确定") {
                        result?.confirm()
                    }
                }.show()
                return true
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                if (_mActivity.isFinishing) {
                    result?.cancel()
                    return true
                }
                alert(message ?: "", "") {
                    positiveButton("确定") {
                        result?.confirm()
                    }
                }.show()
                return true
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (_mActivity.isFinishing) return
//                progress_bar ?: return
//                progress_bar.progress = newProgress
//                if (newProgress == 100) {
//                    progress_bar.visibility = View.GONE
//                    progress_bar.progress = 0
//                }
                super.onProgressChanged(view, newProgress)
            }
        }
        mBind.fragmentIlwWv.webChromeClient = mFileUploadWebChromeClient

        mBind.fragmentIlwWv.settings.javaScriptEnabled = true //允许h5使用javascript
        mBind.fragmentIlwWv.settings.domStorageEnabled = true //允许android调用javascript
        //允许webview对文件的操作
        mBind.fragmentIlwWv.settings.allowUniversalAccessFromFileURLs = true
        mBind.fragmentIlwWv.settings.allowFileAccess = true
        mBind.fragmentIlwWv.settings.allowFileAccessFromFileURLs = true
        mBind.fragmentIlwWv.settings.loadsImagesAutomatically = true
        //android 5.0以上webview 加载https ，才会出现的问题，以下是解决方案
        mBind.fragmentIlwWv.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        mBind.fragmentIlwWv.addJavascriptInterface(object {
            @JavascriptInterface
            fun loadImage(type: Int) {
                if (_mActivity == null || _mActivity.isFinishing) return
                mFileUploadWebChromeClient.onlyCameraAllowed = (type == 1)
            }
        }, "stonejs")
        mBind.fragmentIlwWv.loadUrl("file:///android_asset/test.html")

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun onNeverAskAgain() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        if (requestCode == REQUEST_CODE_PHOTO) {
            data?.data?.let {
//                fragment_sic_iv.setImageURI(it)  //无任务压缩处理，直接加载

                val w = resources.displayMetrics.widthPixels / 3 * 3
                val h = resources.displayMetrics.heightPixels / 3 * 3
                mBind.fragmentIlwIv.setImageBitmap(BitmapUtil.loadBitmapFromUri(it, w, h))

            }
        }

        if (requestCode == ImageChoosePop.TO_CHOOSE_IMAGE) {
            if (data?.data == null) {
                cleanValueCallback()
                return
            }
            val imagePath =
                if (/*data.data?.scheme.equals("file") && */data.type?.contains("image/") == true) {
                    loadImageGetUri(data)
                } else {
                    data.data
                }
            mFileUploadWebChromeClient.valueCallbacks?.onReceiveValue(arrayOf(Uri.parse(data.dataString)))
            mFileUploadWebChromeClient.valueCallbacks = null
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                mFileUploadWebChromeClient.valueCallback?.onReceiveValue(
                    Uri.fromFile(
                        File(
                            KitKatUtil.getRealPathFromURI(
                                _mActivity.applicationContext,
                                imagePath!!
                            )
                        )
                    )
                )
            } else {
                mFileUploadWebChromeClient.valueCallback?.onReceiveValue(
                    Uri.parse(
                        KitKatUtil.getPath(
                            _mActivity.applicationContext,
                            imagePath!!
                        )
                    )
                )
            }

            mFileUploadWebChromeClient.valueCallback = null
            mFileUploadWebChromeClient.mImageChoosePop?.popDismiss()
        } else if (requestCode == ImageChoosePop.TO_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            PictureHelper.currentPhotoPath ?: return
//            val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as? ArrayList<ImageItem>
//            val imagepath = images?.takeIf { it.isNotEmpty() }?.get(0)?.path ?: return
            val imageUri =
                Uri.fromFile(File(PictureHelper.currentPhotoPath)) // images?.takeIf { it.isNotEmpty() }?.get(0)?.uri ?: return
//            val result = Uri.parse(KitKatUtil.getPath(_mActivity.applicationContext, imageUri))
//            var results = arrayOf(FileUtils.getImageContentUri(_mActivity, File(imagepath))!!)

            mFileUploadWebChromeClient.valueCallbacks?.onReceiveValue(arrayOf(imageUri))
            mFileUploadWebChromeClient.valueCallbacks = null

            mFileUploadWebChromeClient.valueCallback?.onReceiveValue(imageUri)
            mFileUploadWebChromeClient.valueCallback = null
        }
        mFileUploadWebChromeClient.mImageChoosePop?.popDismiss()

    }

    private fun loadImageGetUri(intent: Intent): Uri? {
        var uri = intent.data
        val type = intent.type
//        if (uri?.scheme.equals("file") && type?.contains("image/") == true) {
        var path = uri?.encodedPath
        if (path != null) {
            path = Uri.decode(path)
            val cr: ContentResolver = _mActivity.contentResolver
            val buff = StringBuffer()
            buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                .append("'$path'").append(")")
            val cur: Cursor? = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.ImageColumns._ID),
                buff.toString(), null, null)
            var index = 0
            cur?.moveToFirst()
            while (cur?.isAfterLast == false) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID)
                // set _id value
                index = cur.getInt(index)
                cur.moveToNext()
            }
            if (index != 0) {
                val uriTemp = Uri
                    .parse("content://media/external/images/media/"
                            + index)
                if (uriTemp != null) {
                    uri = uriTemp
                }
            }
        }
//        }
        return uri
    }

    private fun cleanValueCallback() {
        mFileUploadWebChromeClient.valueCallbacks?.onReceiveValue(null)
        mFileUploadWebChromeClient.valueCallbacks = null
        mFileUploadWebChromeClient.valueCallback?.onReceiveValue(null)
        mFileUploadWebChromeClient.valueCallback = null
    }


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

}