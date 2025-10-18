package com.stone.stoneviewskt.compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// 在 ui.theme/Shape.kt 中
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),       // 用于 Chip, TextField 等小组件
    medium = RoundedCornerShape(8.dp),      // 用于 Card, Button 等中等组件
    large = RoundedCornerShape(16.dp),      // 用于大的底部弹出层等
    extraLarge = RoundedCornerShape(28.dp)
)