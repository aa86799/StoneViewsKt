package com.stone.stoneviewskt.ui.multimg

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMultiImageBinding
import org.jetbrains.anko.support.v4.alert

class MultiImageFragment : BaseBindFragment<FragmentMultiImageBinding>(R.layout.fragment_multi_image) {

    private var mGrantedCallbackCallback: (() -> Unit)? = null
    private var mDeniedCallbackCallback: (() -> Unit)? = null

    val mRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                if (mGrantedCallbackCallback != null) {
                    mGrantedCallbackCallback?.invoke()
                    return@registerForActivityResult
                }
                Toast.makeText(requireContext(), "granted", Toast.LENGTH_SHORT).show()
            } else {
                if (mDeniedCallbackCallback != null) {
                    mDeniedCallbackCallback?.invoke()
                    return@registerForActivityResult
                }
                Toast.makeText(requireContext(), "denied", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(requireContext())
                    .setTitle("why need it ， please go to setting and open it")
                    .setPositiveButton("confirm") { dialog, _ ->
                    }
                    .create().show()
            }
        }

    private val mChooseAlbumLauncher = requestForResult {
        if (it.resultCode != Activity.RESULT_OK) return@requestForResult
        it.data?.clipData?.let { clipData ->
            val list = mutableListOf<Uri>()
            (0 until clipData.itemCount).forEach {
                list.add(clipData.getItemAt(it).uri)
                Log.i("TAG", "mChooseAlbumLauncher: ${list[it]}")
                if (it == 0) {
                    mBind.ivImg1.setImageURI(list[0])
                }
                if (it == 1) {
                    mBind.ivImg2.setImageURI(list[1])
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBind.btnSelectImage.setOnClickListener {
            albumPermissionHandle {
                mChooseAlbumLauncher.launch(
                    Intent(Intent.ACTION_PICK, null)
                        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                )
            }
        }
//        Intent.createChooser()
    }

    fun requestForResult(block: (ActivityResult) -> Unit): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            block(result)
        }
    }

    fun albumPermissionHandle(granted: (() -> Unit)? = null) {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        checkPermission(permission, grantedCallback = {
            granted?.invoke()
        }, rationaleCallback = {
            alert("加载照片，需要访问您的相册", "提示") {
                positiveButton("确定") { dialog ->
                    mRequestPermissionLauncher.launch(permission)
                }
            }.show()
        }, deniedCallback = {
            alert("加载照片，需要访问您的相册", "提示") {
                positiveButton("确定") { dialog ->
                    toSelfSetting(requireContext())
                }
            }.show()
        })
    }

    fun checkPermission(
        permission: String,
        grantedCallback: (() -> Unit)? = null,
        rationaleCallback: (() -> Unit)? = null,
        deniedCallback: (() -> Unit)? = null
    ) {
        mGrantedCallbackCallback = grantedCallback
        mDeniedCallbackCallback = deniedCallback
        val selfPermission = ContextCompat.checkSelfPermission(requireContext(), permission)
        when {
            selfPermission == PermissionChecker.PERMISSION_GRANTED -> {
                if (grantedCallback != null) {
                    grantedCallback.invoke()
                    return
                }
                Toast.makeText(requireContext(), "granted", Toast.LENGTH_SHORT).show()
            }

            //第一次请求权限被禁止，但未选择【不再提醒】
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission) -> {
                if (rationaleCallback != null) {
                    rationaleCallback.invoke()
                    return
                }
                // show tips, needs permission why it is
                AlertDialog.Builder(requireContext())
                    .setTitle("why need it ")
                    .setNegativeButton("cancel") { dialog, _ ->
                    }
                    .setPositiveButton("confirm") { dialog, _ ->
                        mRequestPermissionLauncher.launch(permission)
                    }
                    .create().show()
            }

            selfPermission == PermissionChecker.PERMISSION_DENIED -> {
                mRequestPermissionLauncher.launch(permission)
            }
        }
    }

    fun toSelfSetting(context: Context) {
        Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", context.packageName, null)
            context.startActivity(this)
        }
    }
}