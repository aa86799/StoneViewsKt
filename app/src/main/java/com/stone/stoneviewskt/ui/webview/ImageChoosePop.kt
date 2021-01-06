package com.stone.stoneviewskt.ui.webview

import android.content.Intent
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.base.SupportActivity
import org.jetbrains.anko.dip
import java.lang.ref.SoftReference

class ImageChoosePop {
    private var softReferenceSelect: SoftReference<PopupWindow>? = null
    private var activityContext: SoftReference<SupportActivity>? = null
    private var webViewFragment: BaseFragment
    private var acceptType: String? = null

    constructor(webViewFragment: BaseFragment, acceptType: String?) {
        activityContext = SoftReference(webViewFragment.getCurActivity())
        this.webViewFragment = webViewFragment
        this.acceptType = acceptType
    }

    fun popSelectorView(onFinishBlock: () -> Unit, onlyCamera: Boolean = false) {
        if (softReferenceSelect == null) {
            initChoosePop(onFinishBlock, onlyCamera)
        } else {
            if (softReferenceSelect != null && softReferenceSelect?.get() == null) {
                initChoosePop(onFinishBlock, onlyCamera)
                return
            }
            val popWindow: PopupWindow = softReferenceSelect?.get()!!
            if (popWindow.isShowing)
                popWindow.dismiss()
            else {
                showPop(popWindow, activityContext?.get()?.window?.decorView!!, onFinishBlock, onlyCamera)
            }
        }
    }

    private fun showPop(popWindow: PopupWindow, rootView: View, block: () -> Unit, onlyCamera: Boolean) {
        val lp = activityContext?.get()?.window?.attributes ?: return
        lp.alpha = 0.5f // 0.0-1.0
        activityContext?.get()?.window?.attributes = lp
        popWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0)
        popWindow.setOnDismissListener {
            lp.alpha = 1f // 0.0-1.0
            activityContext?.get()?.window?.attributes = lp
            block()
        }
        if (onlyCamera) {
            takePhoto()
            popWindow.contentView.visibility = View.INVISIBLE
        } else popWindow.contentView.visibility = View.VISIBLE
    }

    public fun popDismiss() {
        softReferenceSelect?.get()?.dismiss()
    }

    // 选择相机还是相册
    private fun initChoosePop(block: () -> Unit, onlyCamera: Boolean) {
        val rootView = activityContext?.get()?.window?.decorView ?: return
        val resources = activityContext?.get()?.resources ?: return

        val contentView = LayoutInflater.from(rootView.context).inflate(R.layout.pop_choose, null)
        val popWindow = PopupWindow(
                contentView, resources.displayMetrics.widthPixels, activityContext?.get()?.dip(160)
                ?: 160
        )
        softReferenceSelect = SoftReference(popWindow)

        popWindow.isOutsideTouchable = true
        popWindow.isFocusable = true // 拦截外部事件
        popWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.back_rect_round))

        showPop(popWindow, rootView, block, onlyCamera)

        contentView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            popWindow.dismiss()
        }

        contentView.findViewById<Button>(R.id.btn_take_photo).setOnClickListener {
            takePhoto()
        }
        contentView.findViewById<Button>(R.id.btn_choose_album).setOnClickListener {
            chooseAlbum()
        }
    }

    open fun takePhoto(requestCode: Int = TO_TAKE_PHOTO) {
        activityContext?.get()?.takeIf { !it.isFinishing } ?: return
        val configAuthority = "com.stone.stoneviewskt"

        if (TextUtils.isEmpty(configAuthority)) {
            throw RuntimeException("============>  authority is empty <=============")
        }

        PictureHelper.dispatchTakePictureIntent(activityContext?.get()!!, configAuthority!!) { intent ->
            webViewFragment.startActivityForResult(intent, requestCode)
        }
    }

    fun chooseAlbum(requestCode: Int = TO_CHOOSE_IMAGE) {
        activityContext?.get()?.takeIf { !it.isFinishing } ?: return

        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, if (TextUtils.isEmpty(acceptType)) "*/*" else acceptType)
        webViewFragment.startActivityForResult(intent, requestCode)
    }

    companion object {
        const val TO_TAKE_PHOTO = 0xa4
        const val TO_CHOOSE_IMAGE = 0xa5
    }
}
