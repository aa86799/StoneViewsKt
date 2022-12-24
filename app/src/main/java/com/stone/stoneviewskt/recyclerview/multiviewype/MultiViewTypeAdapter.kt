package com.stone.stoneviewskt.recyclerview.multiviewype

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.adapter.BaseRvMultiAdapter
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.recyclerview.multiviewype.data.AddressDataWrap
import com.stone.stoneviewskt.recyclerview.multiviewype.data.UserDataWrap
import com.stone.stoneviewskt.recyclerview.multiviewype.data.VIEW_TYPE_ADDRESS

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/12/24 11:49
 */
class MultiViewTypeAdapter : BaseRvMultiAdapter<Any>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_ADDRESS -> BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list1, parent, false))
            // VIEW_TYPE_USER
            else -> BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list2, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val data = dataset[position]) {
            is AddressDataWrap -> data.viewType
            is UserDataWrap -> data.viewType
            else -> super.getItemViewType(position)
        }
    }

    override fun fillData(holder: BaseViewHolder, position: Int, data: Any) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ADDRESS -> {
                val btn = holder.itemView.findViewById<Button>(R.id.item_simple_list_tv)
                btn.text = "item_simple_list1 ---- No.${position + 1}  Data: $data"
            }
            else -> {
                val btn = holder.itemView.findViewById<Button>(R.id.item_simple_list_tv)
                btn.text = "item_simple_list2 ---- No.${position + 1}  Data: $data"
            }
        }
    }

}