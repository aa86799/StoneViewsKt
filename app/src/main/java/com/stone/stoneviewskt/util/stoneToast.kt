package com.stone.stoneviewskt.util

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stone.stoneviewskt.R

fun stoneToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    val view = LayoutInflater.from(context).inflate(R.layout.toast_text_layout,null)
    val text: TextView = view.findViewById(R.id.tv_message)
    text.text = message
    val toast = Toast(context)
    toast.setGravity(Gravity.CENTER,0,0)
    toast.duration = duration
    toast.view = view
    toast.show()
}

fun stoneToast(context: Context, message: Int) {
    stoneToast(context, context.getString(message))
}

fun Activity.stoneToast(message: String) {
    stoneToast(this, message)
}

fun Fragment.stoneToast(message: String) {
    stoneToast(requireContext(), message)
}

fun Activity.stoneToast(message: Int) {
    stoneToast(this, getString(message))
}

fun Fragment.stoneToast(message: Int) {
    stoneToast(requireContext(), getString(message))
}
