package com.stone.stoneviewskt.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.common.inflate
import com.stone.stoneviewskt.databinding.ItemSimpleList2Binding

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 11:14
 */
class SampleAdapter2(private val list: List<String>? = listOf(), private val itemClick: (Int, String)-> Unit): RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list2, parent, false))
        return BaseViewHolder(inflate<ItemSimpleList2Binding>(parent))
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bind = holder.mBind as ItemSimpleList2Binding
        list?.get(position)?.let { data ->
            bind.itemSimpleListTv.text = data

            bind.itemSimpleListTv.setOnClickListener {
                itemClick(position, data)
            }
        }
    }

//     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////         var tv: TextView = itemView.findViewById(android.R.id.text1)
//         var tv: Button = itemView.findViewById(R.id.item_simple_list_tv)
//     }
}