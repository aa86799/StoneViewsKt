package com.stone.stoneviewskt.ui.video

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.lifecycleScope
import com.iceteck.silicompressorr.VideoCompress
import com.iceteck.silicompressorr.VideoCompress.CompressListener
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_video_compress.*
import kotlinx.coroutines.async
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File

/**
 * desc:    视频压缩
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/2 19:24
 */
@RuntimePermissions
class VideoCompressFragment: BaseFragment() {

    private var mPath: String = ""

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)


        fragment_video_compress_btn.setOnClickListener {
            lifecycleScope.async {
//                val mediaEntity = MediaEntity.newBuilder()
//                    .localPath(mPath)
//                    .fileType(MimeType.ofVideo())
//                    .compressPath("${_mActivity.externalCacheDir}/stone.mp4")
//                    .mimeType(MimeType.createVideoType(mPath))
//                    .build()
//                val option = PhoenixOption().apply {
//                    enableCompress(true)
//                }
//                val m = compressVideoProcessor?.syncProcess(requireContext(), mediaEntity, option)


                val destPath = "${_mActivity.externalCacheDir}/stone.mp4"
                VideoCompress.compressVideoLow(mPath, destPath, object : CompressListener {
                    override fun onStart() {
//                        startTime = System.currentTimeMillis()
//                        setTime(startTime, "开始时间")
                    }

                    override fun onSuccess() {
//                        endTime = System.currentTimeMillis()
//                        setTime(endTime, "结束时间")
//                        logi("压缩后大小 = " + getFileSize(destPath))
                        logi("压缩后大小 = ${(File(destPath).length()/ 1024f) / 1024f}mb")
//                        openFile(File(destPath))
                    }

                    override fun onFail() {
//                        endTime = System.currentTimeMillis()
//                        setTime(endTime, "失败时间")
                    }

                    override fun onProgress(percent: Float) {
                        logi("$percent%")
                    }
                })
            }

        }

        fragment_video_compress_choose.setOnClickListener {
            chooseVideoWithPermissionCheck()
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun chooseVideo() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*")
//            intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/mpeg")
        startActivityForResult(intent, 0x102)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode == 0x102) {
            mPath = getRealFilePath(data.data) ?: return
            val uri = data.data
        }
    }

    private fun getRealFilePath(uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        return when (scheme) {
            null -> uri.path
            ContentResolver.SCHEME_FILE -> uri.path
            ContentResolver.SCHEME_CONTENT -> {
                val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
                val cursor = requireContext().contentResolver.query(uri, projection, null, null, null)
                cursor?.use {
                    var data: String? = null
                    if (it.moveToFirst()) {
                        cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA).takeIf { it > -1 }?.apply {
                            data = cursor.getString(this)
                        }
                    }
                    data
                }
            }
            else -> null
        }
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_video_compress
    }
}