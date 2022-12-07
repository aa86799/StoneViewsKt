package com.stone.stoneviewskt.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentDialogTestBinding
import com.stone.stoneviewskt.util.logi

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2021/1/24 14:09
 */
class TestDialogFragment : DialogFragment() {

    private lateinit var mBind: FragmentDialogTestBinding

    //已过时
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        logi("onAttach activity")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        logi("onAttach context")
    }

    //dialog fragment 未创建时，不会调用。 若已创建，在onAttach之后 onCreate之前调用。  没测试出来。
    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        logi("onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logi("onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        logi("onCreateDialog")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_dialog_test, null)
        mBind = inflateBinding(inflater, container)
        return mBind.root
        logi("onCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logi("onViewCreated")

//        childFragmentManager.findFragmentById(R.id.fragment_dialog_test_sub) as GrayFragment

        mBind.fragmentDialogTestBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logi("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        logi("onStart")

        dialog?.takeIf { it.window != null }?.apply {
//            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 400)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            val p = window?.attributes
            p?.gravity = Gravity.BOTTOM
            window?.attributes = p

            isCancelable = false
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onResume() {
        super.onResume()
        logi("onResume")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        logi("onCancel")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        logi("onDismiss")
    }

    override fun onPause() {
        super.onPause()
        logi("onPause")
    }

    override fun onStop() {
        super.onStop()
        logi("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logi("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        logi("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        logi("onDetach")
    }

}