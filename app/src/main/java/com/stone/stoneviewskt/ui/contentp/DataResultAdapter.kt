package com.stone.stoneviewskt.ui.contentp

import android.widget.Button
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.adapter.BaseRvAdapter
import com.stone.stoneviewskt.common.BaseViewHolder

class DataResultAdapter(private val data: ArrayList<String>) : BaseRvAdapter<String>(R.layout.item_simple_list2, data) {

    override fun fillData(holder: BaseViewHolder, position: Int, data: String) {
        val btn = holder.itemView.findViewById<Button>(R.id.item_simple_list_tv)
        btn.text = data
    }
}