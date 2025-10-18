package com.stone.stoneviewskt.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.stone.stoneviewskt.compose.ui.theme.AppTheme
import com.stone.stoneviewskt.compose.ui.theme.primaryLight

class ComposeMainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()

        setContent {
            // 显示属性
            val displayFeatures = calculateDisplayFeatures(this)
            // 过滤出 折叠显示属性
//            val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
//            foldingFeature?.state
//            foldingFeature?.orientation
//            foldingFeature?.isSeparating
//            foldingFeature?.occlusionType

            AppTheme {
                // 它们会自动继承 YourAppTheme 中定义的颜色、字体和形状
                Surface(modifier = Modifier.fillMaxSize(), color = primaryLight) {

                }
            }
        }
    }
}