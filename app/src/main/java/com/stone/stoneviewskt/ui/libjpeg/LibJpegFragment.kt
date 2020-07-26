package com.stone.stoneviewskt.ui.libjpeg

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_lib_jpeg.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.File

/**
 * desc:    libjpeg-turbo-2.0.5    jpeg图片处理库
 *          https://libjpeg-turbo.org/
 *          https://github.com/libjpeg-turbo/libjpeg-turbo/blob/master/BUILDING.md
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 17:20
 */
class LibJpegFragment : BaseFragment() {

    init {
        System.loadLibrary("native-lib")
    }

    @SuppressLint("ResourceType")
    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)


        val count = resources.openRawResource(R.drawable.kotlin).available()
        fragment_lib_jpeg_before.text = "压缩前原始字节数：$count"

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.kotlin)
        fragment_lib_jpeg_ok.onClick {
            requestPermission()
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                compressJpeg(bitmap, 80, "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/jsy.jpg")  //路径方法 过时了
                logi(mActivity.cacheDir.absolutePath)
                val output = "${mActivity.cacheDir}/kotlin.jpg"
                compressJpeg(bitmap, 80, output)
                fragment_lib_jpeg_iv.setImageURI(File(output).toUri())
                fragment_lib_jpeg_after.text = "压缩后原始字节数 ${File(output).length()}"
            }
        }
    }

    private external fun compressJpeg(bitmap: Bitmap, quality: Int, path: String)

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(mActivity, "申请权限", Toast.LENGTH_SHORT).show()

            // 申请权限
            ActivityCompat.requestPermissions(mActivity, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE), 100);
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_lib_jpeg
    }


}