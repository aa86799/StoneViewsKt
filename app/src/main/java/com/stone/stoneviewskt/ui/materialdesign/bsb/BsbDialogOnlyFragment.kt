package com.stone.stoneviewskt.ui.materialdesign.bsb

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_bsb.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/11 16:14
 */
class BsbDialogOnlyFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        fragment_bsb_sheet.visibility = View.GONE

        fragment_bsb_sheet_btn.setOnClickListener {
            showSheetDialog()
        }

        fragmentManager?.run {
            BsbDialogFragment().show(this, "BsbDialogFragment")
        }
    }

    private fun showSheetDialog() {
        context ?: return

        val view = View.inflate(context, R.layout.dialog_test, null)
        //margin要使用 style。style中的dp-margin 效果翻倍了的感觉。 通常底部滑动出现，最终连接底部的，不需要margin。
        val dialog = BottomSheetDialog(context!!, R.style.MyBottomSheetDialog)
//        dialog.setContentView(R.layout.dialog_test)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)

//        val behavior = BottomSheetBehavior.from(view.parent as View)
        val behavior =  dialog.behavior
//        behavior.peekHeight = 200
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dialog.dismiss()
                }
            }
        })
        view.findViewById<View>(R.id.close_tv).setOnClickListener {
            behavior.isHideable = true
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        dialog.show()

    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_bsb
    }

}