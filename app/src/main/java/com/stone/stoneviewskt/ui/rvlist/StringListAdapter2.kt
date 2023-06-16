package com.stone.stoneviewskt.ui.rvlist
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.showShort

/**
 * desc:    内部基于 AsyncListDiffer 实现
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/11 14:27
 */
class StringListAdapter2 : RecyclerView.Adapter<BaseViewHolder>() {

    /*
     * DiffUtil 的本质 是对比新旧数据
     *   calculateDiff(): DiffResult 计算 新旧集合数据间的差异
     *   再 diffResult.dispatchUpdatesTo(mAdapter) ，自动调用
     *      notifyItemRangeInserted
     *      notifyItemRangeRemoved
     *      notifyItemMoved
     *      notifyItemRangeChanged  (源码中没有这么直接调用，但最终肯定是调用了)
     *
     * todo 集合内数据的重复 和 DiffUtil 没有任何关系
     */

    /*
     * 由于 DiffUtil 是对比新旧数据，所以需要创建新的集合来存放新数据
     * 然后调用 differ.submitList
     *
     */

    /*
     * DiffUtil.calculateDiff(mDiffCallback)是一个耗时操作 ，默认会发生在主线程
     * 需要我们放到子线程去处理；
     * AsyncListDiffer 是异步的 比较
     */
    val differ = AsyncListDiffer<String>(this, object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            logi("StringListAdapter2-areItemsTheSame ${oldItem == newItem}")
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            // 不一致时, 就认为此Item数据有更新
//            logi("StringListAdapter2-areContentsTheSame ${oldItem == newItem}")
            return oldItem == newItem
        }

        private val payloadResult = Any()
        override fun getChangePayload(oldItem: String, newItem: String): Any {
            // payload 用于Item局部字段更新的时候使用,具体用法可以自行搜索了解
            // 当检测到同一个Item有更新时, 会调用此方法
            // 此方法默认返回null, 此时会触发Item的更新动画, 表现为Item会闪一下
            // 当返回值不为null时, 可以关闭Item的更新动画
            return payloadResult
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list2, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val btn = holder.itemView.findViewById<Button>(R.id.item_simple_list_tv)
        btn.text = "No.${position + 1}  adapterPosition:${holder.absoluteAdapterPosition} Data: ${differ.currentList[position]}"
        logi("onBindViewHolder ${differ.currentList.size}  ${differ.currentList}")

        /*
         * differ 删除后， 其它位置不会刷新
         * 造成 position 还是原来的 position
         * 如下点击事件 就可能造成 IndexOutOfBoundsException
         *
         */
        btn.setOnClickListener {
            showShort("$position click  value=${differ.currentList[position]}")
        }
    }

    fun submitList(newList: List<String>) {
        // 创建新的List的浅拷贝, 再调用submitList
        differ.submitList(newList)
    }

    fun addItem(item: String) {
        // 创建新的List的浅拷贝, 再调用submitList
        val listNew = arrayListOf<String>()
        listNew.addAll(differ.currentList)
        listNew.add(item)
        differ.submitList(listNew)
    }

    fun removeItem(data: String) {
        val listNew = arrayListOf<String>()
        listNew.addAll(differ.currentList)
        listNew.remove(data)
        differ.submitList(listNew)
        logi("removeItem")
    }

    fun clear() {
        differ.submitList(null)
    }
}