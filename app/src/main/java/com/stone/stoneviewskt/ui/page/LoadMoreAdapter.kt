package com.stone.stoneviewskt.ui.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseViewHolder
import com.stone.stoneviewskt.util.logi

/**
 * desc:    加载更多
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/8 16:11
 */
class LoadMoreAdapter(private val retryBlock: () -> Unit) : LoadStateAdapter<BaseViewHolder>() {
    override fun onBindViewHolder(holder: BaseViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> { // 非加载中
                logi("LoadMoreAdapter---onBindViewHolder---LoadState.NotLoading")
                holder.itemView.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                holder.itemView.findViewById<TextView>(R.id.tv_load).visibility = View.GONE

                holder.itemView.findViewById<TextView>(R.id.tv_all_message).visibility = View.VISIBLE

                holder.itemView.findViewById<TextView>(R.id.btn_retry).visibility = View.GONE
            }

            is LoadState.Loading -> { // 加载中
                holder.itemView.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                holder.itemView.findViewById<TextView>(R.id.tv_load).visibility = View.VISIBLE

                holder.itemView.findViewById<TextView>(R.id.tv_all_message).visibility = View.GONE

                holder.itemView.findViewById<TextView>(R.id.btn_retry).visibility = View.GONE
            }

            is LoadState.Error -> {
                holder.itemView.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                holder.itemView.findViewById<TextView>(R.id.tv_load).visibility = View.GONE

                holder.itemView.findViewById<TextView>(R.id.tv_all_message).visibility = View.GONE

                val btnRetry = holder.itemView.findViewById<TextView>(R.id.btn_retry)
                btnRetry.visibility = View.VISIBLE
                btnRetry.text = "发生了错误：${loadState.error.message}，点击重试"
                btnRetry.setOnClickListener {
                    retryBlock()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_load_more, parent, false)
        return BaseViewHolder(itemView)
    }

    // 是否作为 item 显示
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        logi("loadState -- $loadState")
        return loadState is LoadState.Loading
                || loadState is LoadState.Error
                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
        // loadState.endOfPaginationReached false 表示有更多的数据要加载
    }

}