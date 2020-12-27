package com.stone.stoneviewskt.ui.jetpack.datastore

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.data.SCaches
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/12/27 08:46
 */
class DataStoreFragment: BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launchWhenResumed {
//            flowOf(1..3).collect {
//                view.findViewById<TextView>(android.R.id.text1)?.text = it.toString()
//            }
//            delay(1000)
//            flowOf(7..9).collect {
//                view.findViewById<TextView>(android.R.id.text1)?.text = it.toString()
//            }
//        }

        //第二个总是无法执行。 这是bug 吧 todo
        lifecycleScope.launchWhenResumed {
            SCaches.put("str_name", "stone")
            SCaches.getString("str_name").collect {
                it?.run {
                    view.findViewById<TextView>(android.R.id.text1)?.text = this
                }
            }
            delay(1000)
            logi("SCaches call second")  //这里调用不到，无论上面delay多久  //todo
            SCaches.put("int_num", 9988)
            SCaches.getInt("int_num").collect {
                it?.let { v ->
                    val text = view?.findViewById<TextView>(android.R.id.text1)?.text.toString()
                    view.findViewById<TextView>(android.R.id.text1)?.text = "$text $v"
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return android.R.layout.simple_list_item_1
    }
}