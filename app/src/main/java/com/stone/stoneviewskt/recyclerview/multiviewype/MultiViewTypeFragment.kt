package com.stone.stoneviewskt.recyclerview.multiviewype

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentMultiViewTypeBinding
import com.stone.stoneviewskt.recyclerview.multiviewype.data.AddressDataWrap
import com.stone.stoneviewskt.recyclerview.multiviewype.data.UserDataWrap
import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.ui.room.data.UserData
import kotlin.random.Random

/**
 * desc   : 描述
 * author : stone
 * email  : aa86799@163.com
 * time   : 2022/12/4 12:22
 */

class MultiViewTypeFragment : BaseBindFragment<FragmentMultiViewTypeBinding>(R.layout.fragment_multi_view_type) {

    private val mAdapter: MultiViewTypeAdapter = MultiViewTypeAdapter()

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.rvData.adapter = mAdapter

        val list = arrayListOf<Any>()
        (1..10).forEach {
            val data: Any = if (it % 3 == 0) {
                AddressDataWrap(AddressData(it, "stone", "123456", "addr-${Random.nextInt()}"))
            } else {
                UserDataWrap(UserData(it * 10, "stone", "123456", 0))
            }
            list.add(data)
        }
        mAdapter.updateData(list)

    }


}