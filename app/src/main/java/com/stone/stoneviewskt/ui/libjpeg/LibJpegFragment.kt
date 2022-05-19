package com.stone.stoneviewskt.ui.libjpeg

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentLibJpegBinding
import com.stone.stoneviewskt.util.logi
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * desc:    libjpeg-turbo-2.0.5    jpeg图片处理库
 *          https://libjpeg-turbo.org/
 *          https://github.com/libjpeg-turbo/libjpeg-turbo/blob/master/BUILDING.md
 *          通过压缩处理后，存储的图片size 可能会大过 原始的图片size；
 *              但其加载出的 Bitmap 大小 会小过， 直接 BitmapFactory.decodeResource 加载的大小。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 17:20
 */
class LibJpegFragment : BaseBindFragment<FragmentLibJpegBinding>() {

    init {
        System.loadLibrary("native-lib")
    }

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentLibJpegBinding {
        return inflateBinding(inflater, container)
    }

    @SuppressLint("ResourceType")
    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

//        val count = resources.openRawResource(R.drawable.kotlin).available()
        val count = resources.openRawResource(R.mipmap.back_girl).available()
        mBind.fragmentLibJpegBefore.text = "图片原始字节数(不受图片目录的密度影响)：$count 字节(B) = ${count / 1024}KB"

//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.kotlin)
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.back_girl) //受dpi目录影响，会自动缩放
//        val bitmap = BitmapFactory.decodeStream(resources.openRawResource(R.mipmap.back_girl)) //不会自动缩放
        mBind.fragmentLibJpegOk.onClick {
            requestPermission()
            if (ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                compressJpeg(bitmap, 80, "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/jsy.jpg")  //路径方法 过时了
                logi(_mActivity.cacheDir.absolutePath)
                val output = "${_mActivity.cacheDir}/kotlin.jpg"
                var quality = 100
                do {
                    compressJpeg(bitmap, quality, output)
                    val outFile = File(output)
                    val outFileLength = outFile.length()
                    val baos = ByteArrayOutputStream()
                    val op = BitmapFactory.decodeFile(output)
                        .compress(Bitmap.CompressFormat.JPEG, quality, baos)
                    mBind.fragmentLibJpegAfter.text = """
> 存储bitmap像素所占内存: ${bitmap.byteCount} 字节(B) = ${bitmap.byteCount / 1024}KB；
> bitmap所占像素已经分配的大小: ${bitmap.allocationByteCount} 字节(B) = ${bitmap.allocationByteCount / 1024}KB；
> 上面的值，就是在当前设备的屏幕密度基础上，加载目标目录下 某dpi 密度，经缩放后的，实际占内存大小；
> Bitmap 占用内存大小 = 宽度像素 x （inTargetDensity / inDensity） x 高度像素 x （inTargetDensity / inDensity）x 一个像素所占的内存(Bitmap.Config);
        inDensity 是 Bitmap原始像素密度，即设备屏幕密度； inTargetDensity 是 drawable或mipmap 目录密度；
> 最终压缩后,再存储的图片原始字节数 $outFileLength  字节(B) = ${outFileLength / 1024}KB
> 最终压缩后，再加载成Bitmap的字节数 ${if (op) baos.toByteArray().size else 0}  字节(B) = ${baos.toByteArray().size / 1024}KB
                """

                    quality -= 2 //质量递减
                } while (outFileLength > 30 * 1024) //大于30k

                mBind.fragmentLibJpegIv.setImageURI(File(output).toUri())
            }
        }
    }

    private external fun compressJpeg(bitmap: Bitmap, quality: Int, path: String)

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(_mActivity, "申请权限", Toast.LENGTH_SHORT).show()

            // 申请权限
            ActivityCompat.requestPermissions(
                _mActivity, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 100
            );
        }
    }


}