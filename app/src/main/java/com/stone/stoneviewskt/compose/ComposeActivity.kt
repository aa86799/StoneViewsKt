package com.stone.stoneviewskt.compose

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
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
                    modifier = Modifier
                        .alpha(0.5f)
                        .then(Modifier.background(Color.Red)),
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

    }

    @ExperimentalUnitApi
    @Composable
    fun setTitle(title: String) {
        Text("$title", fontSize = TextUnit(18f, TextUnitType.Sp), textAlign = TextAlign.Center)
    }

    @ExperimentalUnitApi
    @Preview(name = "Light Mode", showBackground = true, backgroundColor = 0xccFFFF00, heightDp = 150) // 命名为浅色主题，展示背景色
//    @Preview(
//        uiMode = Configuration.UI_MODE_NIGHT_YES,
//        showBackground = true,
//        name = "Dark Mode" // 命名为深色主题
//    )
    @Composable
    fun Preview1() {
//        loadRoot(Message("hello aaaaaaabbbb", "stone"))

//        AppTheme { // 依据系统是否是深色主题，来切换主题
//            materialMessageCard(Message("hello aaaaaaabbbb", "stone"))
//        }

        PreviewConversation()
    }

    @Composable
    fun materialMessageCard(msg: Message) {
        val img = android.graphics.Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(img)
        canvas.drawColor(android.graphics.Color.RED)

        // 颜色、排版、形状
        Row(modifier = Modifier.padding(all = 10.dp)) {
//            Image(ImageBitmap.imageResource(id = R.mipmap.satellite_music),
            Image(img.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            /*
             * remember 将本地状态存储在内存中，MutableState 值更新时，
             * 系统会自动重新绘制使用此状态的可组合项（及其子项）
             */
            var isExpanded by remember { mutableStateOf(false)}
            // 当所提供的targetValue被更改时，动画将自动运行
            val surfaceColor: Color by animateColorAsState(
                if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )
//            Column(Modifier.wrapContentSize(unbounded = true)
            // 点击后，更新 isExpanded
            Column(Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 3.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp) // 内容大小动画
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(8.dp, 10.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(list: List<Message>) {
        LazyColumn() {
            items(list) { message ->
                materialMessageCard(message)
            }
        }
    }

    @Composable
    fun PreviewConversation() {
        AppTheme {
            val list = mutableListOf<Message>()
            (1..5).forEach {
                list.add(Message("author$it", "醉里挑灯看剑，梦回吹角连营。八百里分麾下炙，" +
                        "五十弦翻塞外声，沙场秋点兵。马作的卢飞快，弓如霹雳弦惊。了却君王天下事，赢得生前身后名。可怜白发生！-$it"))
            }
            Conversation(list)
        }
    }

    enum class SurfaceState {
        Pressed, Released
    }
    @Preview
    @Composable
    fun PressedSurface() {
        // 看源码发现，pressed 、onPress 就是 MutableState 的 component1、component2;
        // component1 是get ，component2是set
        val (pressed, onPress) = remember { mutableStateOf(false) }
        val transition = updateTransition(
            targetState = if (pressed) SurfaceState.Pressed else SurfaceState.Released
        )

        val height by transition.animateDp { state ->
            when (state) {
                SurfaceState.Released -> 50.dp
                SurfaceState.Pressed -> 350.dp
            }
        }
        val surfaceColor by transition.animateColor { state ->
            when (state) {
                SurfaceState.Released -> Color.Blue
                SurfaceState.Pressed -> Color.Red
            }
        }
        val selectedAlpha by transition.animateFloat { state ->
            when (state) {
                SurfaceState.Released -> 0.5f
                SurfaceState.Pressed -> 1f
            }
        }

        Surface(
            color = surfaceColor.copy(alpha = selectedAlpha),
            modifier = Modifier
                .toggleable(value = pressed, onValueChange = onPress)
                .height(height)
                .fillMaxWidth()
        ){}
    }
}