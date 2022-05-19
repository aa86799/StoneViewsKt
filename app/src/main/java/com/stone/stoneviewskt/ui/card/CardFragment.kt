package com.stone.stoneviewskt.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentCardBinding

/**
 * desc:    卡片
 *      卡片效果 会阻碍 快速浏览。轻量干净的同类内容，不适合用卡片来突出展示。
 *      卡片集合内的卡片可以包含一个唯一的数据组，例如带有动作的清单，带有动作的笔记以及带有照片的笔记。
 *      使用卡内的层次结构来引导用户注意最重要的信息。
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/5/12 11:00
 */
class CardFragment : BaseBindFragment<FragmentCardBinding>() {

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentCardBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.fragmentCardCvTop.setOnLongClickListener {
            // isChecked = true ，会显示选中状态的 一个 icon，默认是 对勾；可通过 android: checkedIcon 来设置
            // 默认是 @drawable/ic_mtrl_checked_circle.xml ；  必须是一个vector 的 drawable， 普通的png，将只保留形状
            mBind.fragmentCardCvTop.isChecked = !mBind.fragmentCardCvTop.isChecked
            true
        }
        //可访问代理。无障碍服务，语音阅读
        mBind.fragmentCardCvTop.accessibilityDelegate = object : View.AccessibilityDelegate() {
            //...
        }

        mBind.fragmentCardMain.addDraggableChild(mBind.fragmentCardCvTop)
        mBind.fragmentCardMain.setViewDragListener(object : DraggableLinearLayout.ViewDragListener {
            override fun onViewCaptured(view: View, i: Int) {

            }

            override fun onViewReleased(view: View, v: Float, v1: Float) {

            }
        })
    }

}