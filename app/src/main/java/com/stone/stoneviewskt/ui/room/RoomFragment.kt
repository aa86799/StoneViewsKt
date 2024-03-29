package com.stone.stoneviewskt.ui.room

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentRoomBinding
import com.stone.stoneviewskt.ui.room.data.AddressData
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.showShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/4 15:04
 */
class RoomFragment : BaseBindFragment<FragmentRoomBinding>(R.layout.fragment_room) {

    var mId = 0L
    var mAddressData: AddressData? = null

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        val range = 0..abs(Random.nextInt())
        mBind.fragmentRoomInsert.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                withContext(Dispatchers.IO) {
                    logi("id: ${range.random()}")
                    appDatabase.addressDao()
                        .insertAndReplace(AddressData(range.random(), "stone-${Random.nextInt()}", "123${Random.nextInt()}", "addr-${Random.nextInt()}"))
                }

            }
        }

        mBind.fragmentRoomDelete.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    val result = appDatabase.addressDao().delete(it)
                    mBind.fragmentRoomResult.text = "delete: $result"
                }
            }
        }

        mBind.fragmentRoomUpdate.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                mAddressData?.let {
                    it.phone = "1999999999"
                    val result = appDatabase.addressDao().update(it)
                    mBind.fragmentRoomResult.text = "update: $result"
                }
            }
        }

        mBind.fragmentRoomQuery.setOnClickListener {
            lifecycleScope.launchWhenResumed {
//               mAddressData = addDatabase.addressDao().queryItem(mId)
                mAddressData = appDatabase.addressDao().queryList().takeIf { it.isNotEmpty() }?.last()
                if (mAddressData == null) {
                    mBind.fragmentRoomResult.text = "queryItem is empty"
                } else {
                    mBind.fragmentRoomResult.text = "queryItem: $mAddressData"
                }

                mBind.fragmentRoomAll.text = appDatabase.addressDao().queryList().joinToString("\n")

                showShort("${appDatabase.addressDao().queryByCondition("stone").size}")
            }
        }


    }
}