package com.stone.camerax

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.view.Surface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.stone.camerax.databinding.ActivityCamerax1Binding
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mBind: ActivityCamerax1Binding

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private val executor = Executors.newFixedThreadPool(3)
    private lateinit var imageCapture: ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = ActivityCamerax1Binding.inflate(layoutInflater)
        setContentView(mBind.root)

        if (allPermissionsGranted()) {
            mBind.vPreview.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
//        view_finder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
//            updateTransform()
//        }

        /*
         * 使用PreviewView有一些限制。当使用 PreviewView 时，你不能做以下任何事情:
创建一个SurfaceTexture来设置TextureView和PreviewSurfaceProvider。
从TextureView中检索SurfaceTexture并将其设置为PreviewSurfaceProvider。
从SurfaceView获取Surface，并将其设置为PreviewSurfaceProvider。
如果这些发生，那么预览将停止 streaming frames 到 PreviewView.
         */

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            // Camera provider is now guaranteed to be available
            val cameraProvider = cameraProviderFuture.get()

            // Set up the preview use case to display camera preview.
            val preview = Preview.Builder().build()

            // Set up the capture use case to allow users to take photos.
            imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .setFlashMode(ImageCapture.FLASH_MODE_AUTO)//闪光灯模式
//                    .setTargetAspectRatio(AspectRatio.RATIO_4_3) //长宽比。 4:3是默认长宽比
                /*
                 * 设置分辨率。结合长宽比，CameraX从支持的分辨率中获取最近的分辨率。 默认为 640x480。
                 * setTargetAspectRatio 与 setTargetResolution 不能同时设置
                 * 若主要需要满足长宽比，使用前者；若主要需要满足分辨率，使用后者。
                 */
                    .setTargetResolution(Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels))
//                    .set...
                    .build()

            //方向监听。
            val orientationEventListener = object : OrientationEventListener(this as Context) {
                override fun onOrientationChanged(orientation : Int) {
                    // Monitors orientation values to determine the target rotation value
                    val rotation : Int = when (orientation) {
                        in 45..134 -> Surface.ROTATION_270
                        in 135..224 -> Surface.ROTATION_180
                        in 225..314 -> Surface.ROTATION_90
                        else -> Surface.ROTATION_0
                    }
                    //旋转角度
                    imageCapture.targetRotation = rotation
                }
            }
            orientationEventListener.enable()

            // Choose the camera by requiring a lens facing
            val cameraSelector = CameraSelector.Builder()
//                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)//前置
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)//后置
                    .build()

            // Attach use cases to the camera with the same lifecycle owner
//            val camera = cameraProvider.bindToLifecycle(
//                    this as LifecycleOwner, cameraSelector, preview, imageCapture)

            /*
             * 图像分析用例提供了一个几乎可访问的图像来进行图像处理，应用机器学习以及更多图像分析技术。
             */
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(executor, ImageAnalysis.Analyzer { image ->
                val rotationDegrees = image.imageInfo.rotationDegrees
//                image.cropRect
//                image.format  //CameraX produces images in YUV_420_888 format.
                // insert your code here.
                Log.e("stone->", "${ImageFormat.YUV_420_888 == image.format}")
            })
            /*
             * bindToLifecycle() 前两个主参数，后面是可变参数的 UseCase 类型。
             */
            val camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner, cameraSelector, preview, imageCapture, imageAnalysis)

            /*
             * CameraControl 提供了 点击对焦功能
             */
            val cameraControl = camera.cameraControl
            mBind.vPreview.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    /*
                     * 比如双击，调用下面的函数过多，报 error :  Cancelled by another startFocusAndMetering()
                     */
                    tapToFocus(cameraControl, event.x, event.y, null)
                }
                true
            }

            // Connect the preview use case to the previewView
            preview.setSurfaceProvider(mBind.vPreview.surfaceProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    //点击对焦
    private fun tapToFocus(cameraControl: CameraControl, x: Float, y: Float, point2: MeteringPoint?) {
        val factory = SurfaceOrientedMeteringPointFactory(1.0f, 1.0f)
        val point = factory.createPoint(x, y)
        val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
//            .addPoint(point2, FocusMeteringAction.FLAG_AE) // could have many
            // auto calling cancelFocusAndMetering in 5 seconds
            .setAutoCancelDuration(5, TimeUnit.SECONDS)
            .build()

        val future = cameraControl.startFocusAndMetering(action)
        future.addListener( Runnable {
            val result = future.get()
            // process the result
            if (result.isFocusSuccessful) {

            }
        } , executor)
    }

    private fun clickToTakePhoto() {
        val path = ""
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File(path)).build()
        imageCapture.takePicture(outputFileOptions, executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException)
                {
                    // insert your code here.
                }
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // insert your code here.
                    outputFileResults.savedUri
                }
            })
//        https://github.com/android/camera-samples/blob/3730442b49189f76a1083a98f3acf3f5f09222a3/CameraUtils/lib/src/main/java/com/example/android/camera/utils/YuvToRgbConverter.kt
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = mBind.vPreview.width / 2f
        val centerY = mBind.vPreview.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(mBind.vPreview.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
//        v_TextureView.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                mBind.vPreview.post { startCamera() }
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
