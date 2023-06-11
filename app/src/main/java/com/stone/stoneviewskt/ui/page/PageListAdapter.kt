package com.stone.stoneviewskt.ui.page

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.data.CustomerData
import com.stone.stoneviewskt.util.logi

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/6/10 11:20
 */
class PageListAdapter: PagingDataAdapter<CustomerData, BaseViewHolder>(object : DiffUtil.ItemCallback<CustomerData>() {
    /*
     * 如果 areItemsTheSame 返回 true，但是仍然会展示相同的数据，可能是因为 areContentsTheSame 返回 false
     */

    // 是否是同一条 item
    override fun areItemsTheSame(oldItem: CustomerData, newItem: CustomerData): Boolean {
        logi("areItemsTheSame") // 没有输出该日志  可能是官方的bug(不然实现这个回调有什么意义)
        return oldItem.id == newItem.id
    }

    // 是否内容相同
    override fun areContentsTheSame(oldItem: CustomerData, newItem: CustomerData): Boolean {
        logi("areContentsTheSame") // 没有输出该日志 可能是官方的bug(不然实现这个回调有什么意义)
        return oldItem.id == newItem.id
    }
}) {

    // item 点击事件
    private var mBlock: ((position: Int, data: CustomerData) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.findView<TextView>(R.id.tv_name).text = data.name

        holder.itemView.setOnClickListener {
            mBlock?.invoke(position, data)
        }

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#c0ff00ff"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#c0abc777"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_page_list, parent, false)
        return BaseViewHolder(itemView)
    }

    fun setItemClick(block: (position: Int, data: CustomerData) -> Unit) {
        this.mBlock = block
    }

}