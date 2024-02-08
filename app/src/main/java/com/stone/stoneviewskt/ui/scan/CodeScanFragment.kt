package com.stone.stoneviewskt.ui.scan

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import com.czxkdz.permission.PermissionCheck
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentCodeScanBinding
import com.stone.stoneviewskt.util.stoneToast

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/2/14
 */
class CodeScanFragment : BaseBindFragment<FragmentCodeScanBinding>(R.layout.fragment_code_scan) {

    private val mPermissionCheck = PermissionCheck()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onStart()
        mPermissionCheck.initPermissionLauncher(this)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.btnScan.setOnClickListener {
            // hms 文档: https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/android-overview-0000001050282308
            // 有多种扫码方式
            mPermissionCheck.cameraPermissionHandle(requireActivity(), true) {
                toScan()
            }
        }

        mBind.btnCopy.setOnClickListener {
            val cm = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setPrimaryClip(ClipData.newPlainText("copy text", mBind.tvCode.text.toString()))
            stoneToast("复制完成")
        }
    }

    private fun buildBitmapByContent(content: String): Bitmap? {
        return try {
            val options = HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.RED).setBitmapColor(Color.BLUE).setBitmapMargin(3).create()
            val width = 200
            val height = 200
//            val type = HmsScan.ALL_SCAN_TYPE
            val type = HmsScan.CODE128_SCAN_TYPE
            ScanUtil.buildBitmap("content", type, width, height, options)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // hms 提供相机扫码和导入图片扫码两个功能，提供完整的Activity，不需要开发者开发扫码界面的UI
    private fun toScan() {
        val options = HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
        ScanUtil.startScan(requireActivity(), 0x110, options)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
    }

    // 需要通过 Activity 传递一下 Result，见 BaseActivity.onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x110) {
                val hmsScan = data?.getParcelableExtra<HmsScan?>(ScanUtil.RESULT)
                hmsScan?.getOriginalValue()?.let {
                    mBind.tvCode.text = it
                }
            }
        }
    }
}