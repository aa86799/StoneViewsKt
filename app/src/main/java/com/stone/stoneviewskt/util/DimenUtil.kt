package com.stone.stoneviewskt.util

import android.content.res.Resources
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP

val Int.dp: Int
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
    }
val Int.sp: Int
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
    }
val Float.dp: Float
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
    }
val Float.sp: Float
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)
    }
val Double.dp: Double
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toDouble()
    }
val Double.sp: Double
    get() {
        return TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toDouble()
    }
