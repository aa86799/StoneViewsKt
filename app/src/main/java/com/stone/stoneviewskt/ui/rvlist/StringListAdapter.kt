package com.stone.stoneviewskt.ui.rvlist
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.util.logi

/**
 * desc:    继承自 ListAdapter，传递 DiffUtil.ItemCallback 实例 differ
 *          内部基于 differ
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/11 14:07
 */
class StringListAdapter : ListAdapter<String, BaseViewHolder>(
    object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            logi("StringListAdapter-areItemsTheSame")
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            logi("StringListAdapter-areContentsTheSame")
            return oldItem == newItem
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list2, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val btn = holder.itemView.findViewById<Button>(R.id.item_simple_list_tv)
        btn.text = "No.${position + 1}  Data: ${currentList[position]}"
    }


    fun addItem(item: String) {
        // 创建新的List的浅拷贝, 再调用submitList
        val listNew = arrayListOf<String>()
        listNew.addAll(currentList)
        listNew.add(item)
        submitList(listNew)
    }

    fun removeItem(data: String) {
        val listNew = arrayListOf<String>()
        listNew.addAll(currentList)
        listNew.remove(data)
        submitList(listNew)
        logi("removeItem")
    }

    fun clear() {
        submitList(null)
    }
}