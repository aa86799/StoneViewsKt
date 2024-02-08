package com.stone.stoneviewskt.ui.rvlist

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentStringListBinding
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.delay

/**
 * desc:    RecyclerView.ListAdapter 示例
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/16
 */
class StringListFragment: BaseBindFragment<FragmentStringListBinding>(R.layout.fragment_string_list) {

    private val adapter by lazy {
        StringListAdapter()
    }
    private val adapter2 by lazy {
        StringListAdapter2()
    }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        test1()
//        test2()
    }

    private fun test1() {
        val list = arrayListOf<String>()
        (1..10).forEach {
            list.add("item$it")
        }
        logi("原始的数据2：${list}")
        adapter.submitList(list)
        mBind.rvData.adapter = adapter

        lifecycleScope.launchWhenResumed {
            delay(3000)
            val listNew = arrayListOf<String>()
            listNew.addAll(adapter.currentList)
            (5..10).forEach {
                adapter.addItem("item$it")
                delay(500) // 如果没有这句，仅只有 item10添加进来
            }

            delay(1000)
            (5..10).forEach {
                // list remove(data) 仅删除集合内第一次出现的元素，不会清除所有的重复的元素
                adapter.removeItem("item$it")
                delay(500)
            }
            delay(3000)
            adapter.clear()
        }
    }

    private fun test2() {
        val list = arrayListOf<String>()
        (1..10).forEach {
            list.add("item$it")
        }
        logi("原始的数据2：${list}")
        adapter2.submitList(list)
        mBind.rvData.adapter = adapter2

        lifecycleScope.launchWhenResumed {
            delay(3000)
            val listNew = arrayListOf<String>()
            listNew.addAll(adapter2.differ.currentList)
            (5..10).forEach {
                adapter2.addItem("item$it")
                delay(500) // 如果没有这句，仅只有 item10添加进来
            }

            delay(1000)
            (5..10).forEach {
                // list remove(data) 仅删除集合内第一次出现的元素，不会清除所有的重复的元素
                adapter2.removeItem("item$it")
                delay(500)
            }
            delay(3000)
            adapter.clear()
        }
    }
}