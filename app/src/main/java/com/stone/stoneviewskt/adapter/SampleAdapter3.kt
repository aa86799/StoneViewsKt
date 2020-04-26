package com.stone.stoneviewskt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.stone.stoneviewskt.R

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/4 11:14
 */
class SampleAdapter3(private val list: List<String>? = listOf(), private val itemClick: (Int, String)-> Unit): RecyclerView.Adapter<SampleAdapter3.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list3, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { data ->
            holder.tv.text = data

            holder.tv.setOnClickListener {
                itemClick(position, data)
            }
        }
    }

     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//         var tv: TextView = itemView.findViewById(android.R.id.text1)
         var tv: Button = itemView.findViewById(R.id.item_simple_list_tv)
     }
}