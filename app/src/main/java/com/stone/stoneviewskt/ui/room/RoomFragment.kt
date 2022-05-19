package com.stone.stoneviewskt.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.inflateBinding
import com.stone.stoneviewskt.databinding.FragmentRoomBinding
import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.util.showShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 15:04
 */
class RoomFragment : BaseBindFragment<FragmentRoomBinding>() {

    var mId = 0L
    var mAddressData: AddressData? = null

    override fun getViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentRoomBinding {
        return inflateBinding(inflater, container)
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val range = 0..Random.nextInt()
        mBind.fragmentRoomInsert.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                withContext(Dispatchers.IO) {
                    addDatabase.addressDao()
                        .insert(AddressData(range.random(), "stone", "123456", "add"))
                }

            }
        }

        mBind.fragmentRoomDelete.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    val result = addDatabase.addressDao().delete(it)
                    mBind.fragmentRoomResult.text = "delete: $result"
                }
            }
        }

        mBind.fragmentRoomUpdate.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    it.phone = "1999999999"
                    val result = addDatabase.addressDao().update(it)
                    mBind.fragmentRoomResult.text = "update: $result"
                }
            }
        }

        mBind.fragmentRoomQuery.setOnClickListener {
            lifecycleScope.launchWhenResumed {
//               mAddressData = addDatabase.addressDao().queryItem(mId)
                mAddressData = addDatabase.addressDao().queryList().takeIf { it.isNotEmpty() }?.last()
                if (mAddressData == null) {
                    mBind.fragmentRoomResult.text = "queryItem is empty"
                } else {
                    mBind.fragmentRoomResult.text = "queryItem: $mAddressData"
                }

                mBind.fragmentRoomAll.text = addDatabase.addressDao().queryList().joinToString("\n")

                showShort("${addDatabase.addressDao().queryByCondition("ston").size}")
            }
        }


    }
}