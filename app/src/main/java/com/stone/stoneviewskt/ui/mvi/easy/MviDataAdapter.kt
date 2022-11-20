package com.stone.stoneviewskt.ui.mvi.easy

import android.widget.TextView
import com.stone.stoneviewskt.adapter.BaseRvAdapter
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.ui.mvi.data.MviData

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/11/6 16:24
 */
/*class MviDataAdapter(val data: MutableList<MviData>) : RecyclerView.Adapter<MviDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MviDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MviDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: MviDataViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = "id=${data[position].id},title=${data[position].title}"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class MviDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)*/


class MviDataAdapter : BaseRvAdapter<MviData>(android.R.layout.simple_list_item_1) {

    override fun fillData(holder: BaseViewHolder, position: Int, data: MviData) {
        holder.findView<TextView>(android.R.id.text1).text = "id=${data.id},title=${data.title}"
    }
}