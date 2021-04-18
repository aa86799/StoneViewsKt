package com.stone.stoneviewskt.ui.room

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.util.showShort
import kotlinx.android.synthetic.main.fragment_room.*
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
class RoomFragment : BaseFragment() {

    var mId = 0L
    var mAddressData: AddressData? = null

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val range = 0..Random.nextInt()
        fragment_room_insert.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                withContext(Dispatchers.IO) {
                    addDatabase.addressDao()
                        .insert(AddressData(range.random(), "stone", "123456", "add"))
                }

            }
        }

        fragment_room_delete.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    val result = addDatabase.addressDao().delete(it)
                    fragment_room_result.text = "delete: $result"
                }
            }
        }

        fragment_room_update.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    it.phone = "1999999999"
                    val result = addDatabase.addressDao().update(it)
                    fragment_room_result.text = "update: $result"
                }
            }
        }

        fragment_room_query.setOnClickListener {
            lifecycleScope.launchWhenResumed {
//               mAddressData = addDatabase.addressDao().queryItem(mId)
                mAddressData = addDatabase.addressDao().queryList().takeIf { it.isNotEmpty() }?.last()
                if (mAddressData == null) {
                    fragment_room_result.text = "queryItem is empty"
                } else {
                    fragment_room_result.text = "queryItem: $mAddressData"
                }

                fragment_room_all.text = addDatabase.addressDao().queryList().joinToString("\n")

                showShort("${addDatabase.addressDao().queryByCondition("ston").size}")
            }
        }


    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_room
    }
}