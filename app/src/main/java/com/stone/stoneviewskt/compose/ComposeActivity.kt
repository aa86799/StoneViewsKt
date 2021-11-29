package com.stone.stoneviewskt.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

class ComposeActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 会与 compose 冲突，造成 compose 无法渲染
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            loadRoot()
        }
    }

    @ExperimentalUnitApi
    @Composable
    @Preview
    fun loadRoot() {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            setTitle("hello")
            setTitle("stone")
        }
    }

    @ExperimentalUnitApi
    @Composable
    fun setTitle(title: String) {
        Text("$title", fontSize = TextUnit(18f, TextUnitType.Sp), textAlign = TextAlign.Center)
    }

    @ExperimentalUnitApi
    @Preview
    @Composable
    fun Preview1() {
        loadRoot()
    }
}