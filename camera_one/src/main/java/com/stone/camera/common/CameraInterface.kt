package com.stone.camera.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.hardware.Camera.ShutterCallback
import android.util.Log
import android.view.SurfaceHolder
import com.stone.camera.util.CamParaUtil
import com.stone.camera.util.FileUtil.saveBitmap
import com.stone.camera.util.ImageUtil.getRotateBitmap
import java.io.IOException

class CameraInterface private constructor() {
    private var mCamera: Camera? = null
    private var mParams: Camera.Parameters? = null
    private var isPreviewing = false
    private var mPreviewRate = -1f

    interface CamOpenOverCallback {
        fun cameraHasOpened()
    }

    /**
     * 打开Camera
     *
     * @param callback
     */
    fun doOpenCamera(callback: CamOpenOverCallback) {
        Log.i(TAG, "Camera open....")
        mCamera = Camera.open()
        Log.i(TAG, "Camera open over....")
        callback.cameraHasOpened()
    }

    /**
     * 开启预览
     *
     * @param holder
     * @param previewRate
     */
    fun doStartPreview(holder: SurfaceHolder?, previewRate: Float) {
        Log.i(TAG, "doStartPreview...")
        if (isPreviewing) {
            mCamera!!.stopPreview()
            return
        }
        if (mCamera != null) {
            mParams = mCamera?.parameters
        }
        mParams?.let {
            it.pictureFormat = PixelFormat.JPEG //设置拍照后存储的图片格式
            CamParaUtil.instance?.printSupportPictureSize(it)
            CamParaUtil.instance?.printSupportPreviewSize(it)
            //设置PreviewSize和PictureSize  
            val pictureSize = CamParaUtil.instance?.getPropPictureSize(
                it.supportedPictureSizes, previewRate, 800
            ) ?: return@let
            it.setPictureSize(pictureSize?.width, pictureSize?.height)
            val previewSize = CamParaUtil.instance?.getPropPreviewSize(
                it.supportedPreviewSizes, previewRate, 800
            )
            //            mParams.setPreviewSize(previewSize.width, previewSize.height);
            it.setPreviewSize(1920, 1080)
            mCamera!!.setDisplayOrientation(90)
            CamParaUtil.instance?.printSupportFocusMode(it)
            val focusModes = it.supportedFocusModes
            if (focusModes.contains("continuous-video")) {
                it.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
            }
            mCamera!!.parameters = mParams
            try {
                mCamera!!.setPreviewDisplay(holder)
                mCamera!!.startPreview() //开启预览  
            } catch (e: IOException) {
                e.printStackTrace()
            }
            isPreviewing = true
            mPreviewRate = previewRate
            mParams = mCamera!!.parameters //重新get一次  
            Log.i(
                TAG, "PreviewSize--With = " + it.previewSize.width
                        + "Height = " + it.previewSize.height
            )
            Log.i(
                TAG, "PictureSize--With = " + it.pictureSize.width
                        + "Height = " + it.pictureSize.height
            )
        }
    }

    /**
     * 停止预览，释放Camera
     */
    fun doStopCamera() {
        if (null != mCamera) {
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            isPreviewing = false
            mPreviewRate = -1f
            mCamera!!.release()
            mCamera = null
        }
    }

    /**
     * 拍照
     */
    fun doTakePicture() {
        if (isPreviewing && mCamera != null) {
            mCamera!!.takePicture(mShutterCallback, null, mJpegPictureCallback)
        }
    }

    /* 为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量 */
    //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    private var mShutterCallback = ShutterCallback {
        Log.i(TAG, "myShutterCallback:onShutter...")
    }

    // 拍摄的未压缩原数据的回调,可以为null
    private var mRawCallback = PictureCallback { data, camera ->
        Log.i(TAG, "myRawCallback:onPictureTaken...")
    }

    //对jpeg图像数据的回调,最重要的一个回调
    private var mJpegPictureCallback = PictureCallback { data, camera ->
        Log.i(TAG, "myJpegCallback:onPictureTaken...")
        var bm: Bitmap? = null
        if (null != data) {
            bm = BitmapFactory.decodeByteArray(data, 0, data.size) //data是字节数据，将其解析成位图
            mCamera!!.stopPreview()
            isPreviewing = false
        }
        //保存图片到sdcard  
        if (null != bm) {
            //设置 FOCUS_MODE_CONTINUOUS_VIDEO 之后，myParam.set("rotation", 90)失效。
            //图片竟然不能旋转了，故这里要旋转下  
            val rotaBitmap = getRotateBitmap(bm, 90.0f)
            saveBitmap(rotaBitmap)
        }
        //再次进入预览  
        mCamera!!.startPreview()
        isPreviewing = true
    }

    companion object {
        private const val TAG = "yanzi"
        private var mCameraInterface: CameraInterface? = null

        @JvmStatic
        @get:Synchronized
        val instance: CameraInterface?
            get() {
                if (mCameraInterface == null) {
                    mCameraInterface = CameraInterface()
                }
                return mCameraInterface
            }
    }
}