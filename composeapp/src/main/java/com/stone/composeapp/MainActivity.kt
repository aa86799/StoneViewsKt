package com.stone.composeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Text("Hello Compose!")
//            ScrollableC(Modifier.fillMaxSize()) {
//                (1..3).forEach {
//                    hello()
//                }
//            }
        }
    }

    @Composable
    fun hello() {
        /*
         * 布局排列，默认 Stack：将一个元素放在另一个元素上。
         * Column：使元素按照竖直方向排列.
         * Row：使元素按照水平方向排列.
         */
        Column(
            Modifier.padding(top = 10.dp),

            ) {
            Text("Hello Stone!", modifier = Modifier
                .clickable {
                    Toast
                        .makeText(this@MainActivity, "hello ", Toast.LENGTH_SHORT)
                        .show()
                }
                .background(Color.Blue), color = Color.Red, fontSize = 20.sp)
            Button(onClick = {
                Toast.makeText(this@MainActivity, "click btn", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.padding(10.dp, 18.dp)) {
                Text(text = "this is a btn")
            }
        }

    }

    @Preview
    @Composable
    fun m1() {
        hello()
    }
}