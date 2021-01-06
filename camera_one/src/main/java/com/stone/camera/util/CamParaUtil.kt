package com.stone.camera.util

import android.hardware.Camera
import android.util.Log
import java.util.*
import kotlin.math.abs

class CamParaUtil private constructor() {
    private val sizeComparator = CameraSizeComparator()
    fun getPropPreviewSize(list: List<Camera.Size>, th: Float, minWidth: Int): Camera.Size {
        Collections.sort(list, sizeComparator)
        var i = 0
        for (s in list) {
            if (s.width >= minWidth && equalRate(s, th)) {
                Log.i(TAG, "PreviewSize:w = " + s.width + "h = " + s.height)
                break
            }
            i++
        }
        if (i == list.size) {
            i = 0 //如果没找到，就选最小的size  
        }
        return list[i]
    }

    fun getPropPictureSize(list: List<Camera.Size>, th: Float, minWidth: Int): Camera.Size {
        Collections.sort(list, sizeComparator)
        var i = 0
        for (s in list) {
            if (s.width >= minWidth && equalRate(s, th)) {
                Log.i(TAG, "PictureSize : w = " + s.width + "h = " + s.height)
                break
            }
            i++
        }
        if (i == list.size) {
            i = 0 //如果没找到，就选最小的size  
        }
        return list[i]
    }

    private fun equalRate(s: Camera.Size, rate: Float): Boolean {
        val r = s.width.toFloat() / s.height.toFloat()
        return abs(r - rate) <= 0.03
    }

    inner class CameraSizeComparator : Comparator<Camera.Size> {
        override fun compare(lhs: Camera.Size, rhs: Camera.Size): Int {
            // TODO Auto-generated method stub  
            return when {
                lhs.width == rhs.width -> {
                    0
                }
                lhs.width > rhs.width -> {
                    1
                }
                else -> {
                    -1
                }
            }
        }
    }

    /**打印支持的previewSizes
     * @param params
     */
    fun printSupportPreviewSize(params: Camera.Parameters) {
        val previewSizes = params.supportedPreviewSizes
        for (i in previewSizes.indices) {
            val size = previewSizes[i]
            Log.i(TAG, "previewSizes:width = " + size.width + " height = " + size.height)
        }
    }

    /**打印支持的pictureSizes
     * @param params
     */
    fun printSupportPictureSize(params: Camera.Parameters) {
        val pictureSizes = params.supportedPictureSizes
        for (i in pictureSizes.indices) {
            val size = pictureSizes[i]
            Log.i(TAG, "pictureSizes:width = " + size.width
                    + " height = " + size.height)
        }
    }

    /**打印支持的聚焦模式
     * @param params
     */
    fun printSupportFocusMode(params: Camera.Parameters) {
        val focusModes = params.supportedFocusModes
        for (mode in focusModes) {
            Log.i(TAG, "focusModes--$mode")
        }
    }

    companion object {
        private const val TAG = "yanzi"
        private var myCamPara: CamParaUtil? = null
        val instance: CamParaUtil?
            get() = if (myCamPara == null) {
                myCamPara = CamParaUtil()
                myCamPara
            } else {
                myCamPara
            }
    }
}