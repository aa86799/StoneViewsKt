package com.stone.stoneviewskt.ui.page

import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.adapter.BaseRvAdapter
import com.stone.stoneviewskt.common.BaseViewHolder

/**
 * desc:    空数据
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/9 10:45
 */
class EmptyAdapter: BaseRvAdapter<String>(R.layout.layout_empty) {

    override fun fillData(holder: BaseViewHolder, position: Int, data: String) {

    }
}