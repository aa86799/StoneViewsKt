package com.stone.camera.common

import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.stone.camera.common.CameraInterface.CamOpenOverCallback
import com.stone.camera.common.CameraInterface.Companion.instance
import com.stone.camera.util.DisplayUtil.getScreenRate

class CameraSurfaceView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : SurfaceView(context, attrs), SurfaceHolder.Callback, CamOpenOverCallback {

    private val surfaceHolder: SurfaceHolder

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.i(TAG, "surfaceCreated...")
        val openThread: Thread = object : Thread() {
            override fun run() {
                instance!!.doOpenCamera(this@CameraSurfaceView)
            }
        }
        openThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int,
                                height: Int) {
        Log.i(TAG, "surfaceChanged...")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.i(TAG, "surfaceDestroyed...")
        instance!!.doStopCamera()
    }

    override fun cameraHasOpened() {
        instance!!.doStartPreview(surfaceHolder, getScreenRate(context))
    }

    companion object {
        private const val TAG = "yanzi"
    }

    init {
        surfaceHolder = holder
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT) //translucent半透明 transparent透明
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        surfaceHolder.addCallback(this)
    }
}