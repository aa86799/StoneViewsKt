package com.stone.stoneviewskt.ui.materialdesign

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_bsb.*
import org.jetbrains.anko.displayMetrics

/**
 * desc:    BottomSheetBehavior 使用view支持底部滑动出现与消失。
 *      缺点，必须作为 CoordinatorLayout 的直接子view；
 *           margin_bottom 是无效的，其它三个方向有效(真实场景中，可能要多套一层 view group)；
 *           不一定能覆盖 CoordinatorLayout 的其它直接子view； NestedScrollView 是可以被覆盖的。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/10 20:02
 */
class BsbFragment : BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        //xml 中设置了 相应的 behavior
        val bottomSheetBehavior = BottomSheetBehavior.from(fragment_bsb_sheet)
        //默认true。false时，view会显示在父view的top， 下拖动后在父view的高度一半，再向下拖动，才显示在底部。
//        bottomSheetBehavior.isFitToContents = false
        /*
         * STATE_COLLAPSED 收缩状态，可见，仅显示 peekHeight 的高度，结合 CoordinatorLayout，可以拖上拖下。
         * STATE_EXPANDED  展示状态，初始完全可见，后面 拖上拖下 与 peekHeight 有关。
         * STATE_SETTLING  安置状态，和 STATE_COLLAPSED 表现类似
         * STATE_HIDDEN 隐藏 bottom sheet
         */
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, mActivity.displayMetrics)
                .toInt()

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }
        })

        fragment_bsb_sheet.setOnClickListener {
            bottomSheetBehavior.isHideable = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            bottomSheetBehavior.peekHeight = 10 //这时不起作用， 完全隐藏view
        }

        fragment_bsb_sheet_btn.setOnClickListener {
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.peekHeight = 50
        }

//        BottomSheetDialog
//        BottomSheetDialogFragment
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_bsb
    }
}