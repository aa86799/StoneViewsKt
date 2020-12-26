package com.stone.stoneviewskt.ui.parceldata

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.data.UserData

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/26 20:29
 */
class ParcelDataFragment: BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<UserData>("user")?.let {
            view.findViewById<TextView>(android.R.id.text1).text = it.toString()
        }
    }

    override fun getLayoutRes(): Int {
        return android.R.layout.simple_list_item_1
    }
}