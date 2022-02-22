package com.stone.stonecompose

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.stone.stonecompose.ui.theme.StoneViewsKtTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneViewsKtTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        val manager = getSystemService(CAMERA_SERVICE) as CameraManager
                        manager.cameraIdList.forEach {
                            val code = manager.getCameraCharacteristics(it).get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
                            val isSupport = if (code == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL) {
                                "yes"
                            } else {
                                "no"
                            }
                            Greeting("camera-$it full support: $isSupport")
                        }

                        AndroidView(factory = {
                            val v = LayoutInflater.from(it).inflate(R.layout.layout_camera_preview, null)
                            v
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StoneViewsKtTheme {
        Greeting("Android")
    }
}