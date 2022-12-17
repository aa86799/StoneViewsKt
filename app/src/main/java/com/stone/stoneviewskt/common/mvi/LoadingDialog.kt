package com.stone.stoneviewskt.common.mvi

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.databinding.DialogLoadingBinding
import com.stone.stoneviewskt.util.dp

class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialog) {

    private val binding: DialogLoadingBinding

    init {
        setCanceledOnTouchOutside(false)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_loading, null, false)
        setContentView(binding.root)
        val window = window
        val lp = window!!.attributes
        lp.width = 250.dp
        lp.height = 150.dp
        lp.gravity = Gravity.CENTER
        window.attributes = lp
    }

    /**
     * 设置等待提示信息
     */
    fun setLoadingMsg(msg: String?) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        binding.tvMsg.text = msg
    }
}