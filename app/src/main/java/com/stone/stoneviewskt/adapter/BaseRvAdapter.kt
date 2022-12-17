package com.stone.stoneviewskt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.stone.stoneviewskt.common.BaseViewHolder

/**
 * desc   : 基于 RecyclerView.Adapter 的封装
 * author : stone
 * email  : aa86799@163.com
 * time   : 2022/11/13 13:00
 */
abstract class BaseRvAdapter<T>(@LayoutRes private val itemLayoutId: Int, val dataset: ArrayList<T> = arrayListOf()) : RecyclerView.Adapter<BaseViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener<T>? = null

    fun updateData(data: List<T>?) {
        this.dataset.clear()
        if (!data.isNullOrEmpty())
            this.dataset.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: Set<T>?) {
        this.dataset.clear()
        if (!data.isNullOrEmpty())
            this.dataset.addAll(data)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, item: T) {
        dataset[position] = item
        notifyItemChanged(position)
    }

    fun addItem(data: T) {
        this.dataset.apply {
            add(data)
            notifyItemInserted(size - 1)
        }
    }

    fun addAll(data: List<T>?, isNotifyItemRange: Boolean = false) {
        // eg.  data.size = 5; dataset.size=3;   dataset.addAll(data)  3+5=8;  dataset.size =8;
        if (data.isNullOrEmpty()) return
        this.dataset.apply {
            addAll(data)
            if (!isNotifyItemRange) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(size - data.size, data.size)
            }
        }
    }

    /*
     * notifyItemRemoved()API使删除时带有删除的动画，但是notifyItemRemoved()在使用不当时会造成条目错乱
     * 这是因为删除一条时，position已经变化的缘故。应该使用holder.getAdapterPosition()重新获取position，而不能使用原来的final修饰的position
     *
     * 若不传position，默认-1，应用 notifyDataSetChanged()
     */
    fun removeItem(data: T, position: Int = -1) {
        this.dataset.remove(data)
        if (position >= 0) {
            notifyItemRemoved(position)
        } else {
            notifyDataSetChanged()
        }
    }

    fun removeAll(isNotifyItemRange: Boolean = false) {
        removeAll(dataset, isNotifyItemRange)
    }

    fun removeAll(data: List<T>, isNotifyItemRange: Boolean = false) {
        removeAll(data.toSet(), isNotifyItemRange)
    }

    /*
     * 若 要删除的数据集合，在原 dataset中是 连续的，而不是分散的；
     * 若 是分散的，那只能使用 notifyDataSetChanged() 来通知 UI 更新;
     */
    fun removeAll(data: Set<T>, isNotifyItemRange: Boolean = false) {
        this.dataset.apply {
            val firstRemovePosition = data.firstOrNull()?.let { this.indexOf(it) } ?: return
            if (firstRemovePosition == -1) return
            removeAll(data)
            if (!isNotifyItemRange) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeRemoved(firstRemovePosition, data.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        return BaseViewHolder(itemView)
    }

    /*
     * 使用 holder.adapterPosition，是因为 若有删除操作后，会导致 final 的 position 对应不到item。
     * onBindViewHolder() 中添加点击事件，是因为 若在 onCreateViewHolder() 中添加，此时holder 还没被创建完成，获取 adapterPosition 就会始终是-1
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        fillData(holder, position, dataset[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position, dataset[holder.adapterPosition])
        }
    }

    abstract fun fillData(holder: BaseViewHolder, position: Int, data: T)

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, data: T)
    }
}