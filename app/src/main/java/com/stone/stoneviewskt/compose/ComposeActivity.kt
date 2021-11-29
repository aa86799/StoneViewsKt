package com.stone.stoneviewskt.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.stone.stoneviewskt.R
import org.jetbrains.anko.toast

class ComposeActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 会与 compose 冲突，造成 compose 无法渲染
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Preview1()
        }
    }

    data class Message(val author: String, val body: String)

    @ExperimentalUnitApi
    @Preview
    @Composable
    fun loadRoot(msg: Message) {
        Column(Modifier.size(100.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = msg.author, fontStyle = FontStyle.Italic,
                    modifier = Modifier.alpha(0.5f).then(Modifier.background(Color.Red)),
            overflow = TextOverflow.Ellipsis, softWrap = true, maxLines = 1)
            OutlinedButton(onClick = {
                toast("xxx")
            }) {
                Text(text = msg.body)
            }
            Row(modifier = Modifier.padding(all = 10.dp)) {
                Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "Contact profile picture",
                        modifier = Modifier
                                // Set image size to 40 dp
                                .size(40.dp)
                                // Clip image to be shaped as a circle
                                .clip(CircleShape)
                )
                Column(Modifier.wrapContentSize(unbounded = true)) {
                    setTitle(msg.author)
                    setTitle(msg.body)
                }
            }
        }
        PaddingValues.Absolute(10.dp, 20.dp, 10.dp, 20.dp)
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
        loadRoot(Message("hello aaaaaaabbbb", "stone"))
    }
}