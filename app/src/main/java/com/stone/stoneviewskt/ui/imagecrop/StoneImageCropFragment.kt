package com.stone.stoneviewskt.ui.imagecrop

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.BitmapUtil
import com.stone.stoneviewskt.util.logi
import kotlinx.android.synthetic.main.fragment_stone_image_crop.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/6 11:32
 */
@RuntimePermissions
class StoneImageCropFragment: BaseFragment() {

    companion object {
        private const val REQUEST_CODE_PHOTO = 0x123
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_sic_open_photo.setOnClickListener {
            choosePhotoWithPermissionCheck()
        }

//        fragment_sic_iv

        logi("${getResources().getDisplayMetrics().densityDpi}")
        logi("${getResources().getDisplayMetrics().density}")
        logi("${getResources().getDisplayMetrics().scaledDensity}")

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQUEST_CODE_PHOTO)
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onNeverAskAgain() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        if (requestCode == REQUEST_CODE_PHOTO) {
            data?.data?.let {
                fragment_sic_iv.setImageURI(it)  //无任务压缩处理，直接加载
                
                val w = resources.displayMetrics.widthPixels / 3 * 3
                val h = resources.displayMetrics.heightPixels / 3 * 3
                fragment_sic_iv2.setImageBitmap(BitmapUtil.loadBitmapFromUri(it, w, h))
//                fragment_sic_iv.setImageDrawable(BitmapUtil.loadDrawableFromUri(it, w, h))

            }
        }
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_stone_image_crop
    }
}