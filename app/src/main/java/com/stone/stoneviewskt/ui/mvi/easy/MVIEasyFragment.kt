package com.stone.stoneviewskt.ui.mvi.easy

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.logi
import kotlinx.coroutines.flow.catch

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/11/6
 */
class MVIEasyFragment : BaseFragment() {

    @PublishedApi
    internal val mViewModel by viewModels<MviEasyViewModel>()
    private var mRvData: RecyclerView? = null
    private lateinit var mAdapter: MviDataAdapter
    private var mPage: Int = 0

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mRvData = view?.findViewById(R.id.rv_data)
        mAdapter = MviDataAdapter()
        mRvData?.adapter = mAdapter

        // 订阅状态
        lifecycleScope.launchWhenCreated {
            // 实现时在底层使用了 suspendCancellableCoroutine
            // 会将调用的协程挂起, 然后每当生命周期进入 (或高于) 目标状态时在一个新的协程中执行您作为参数传入的一个挂起块
            // 如果生命周期低于目标状态，因执行该代码块而启动的协程就会被取消
            // 也能达到，当vm是activity级，屏幕旋转后，也不再重复收集的作用
            activity?.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.state
                    // .distinctUntilChanged() // 防抖,过滤掉所有相同值的后续重复. StateFlow 内部默认应用了该操作符，不能主动应用它;sharedFlow 可以
                    .catch { } // exception
                    .collect {
                        when (it) {
                            is MainUiState.Loading -> {
                                logi("show loading view")
                            }
                            is MainUiState.LoadSuccess -> {
                                logi("rv show data, add data of page ${it.page}, dismiss loading view")
                                if (it.page == 1) { // refresh action
                                    mAdapter.updateData(it.data)

                                    // 模拟 区间删除; 注意item 数据要有个类似唯一的标记；否则添加再删除，就可能混乱了； 比如将 MviData#strId 字段注释后，多次点击刷新后就出现问题了
                                    /*if (mAdapter.dataset.size > 6) {
                                        mAdapter.removeAll(mAdapter.dataset.subList(3, 5), true)
                                    }
                                    mAdapter.addAll(it.data)*/
                                } else {
                                    mAdapter.addAll(it.data)
                                }
                            }
                            is MainUiState.LoadError -> {
                                if (mPage > 0) mPage--
                                logi("show error view tips, error message is \"${it.error.message}\"")
                            }
                        }
                    }
            }
//            mViewModel.state
//                    /*
//                     * viewLifecycleOwner fragment的view被destroy时，可能fragment本身在某些时候是不会destroy的，所以用该对象
//                     * flowWithLifecycle() 对flow 指定 lifecycle state，低于时，停止收集数据
//                     * flowWithLifecycleEx 对 flowWithLifecycle() 进行了增强，用于去重复数据的收集
//                     *
//                     * 测试时，将 vm 对象，定义成 activity 级的
//                     * 屏幕旋转后，onCreate()重新执行，但vm 对象不变，之前的 collect也没有停止；又会再次 collect()
//                     * 而指定了 flowWithLifecycle()，fragment的 view 会destroy，会停止前一个收集，并取消前一个收集的协程
//                     */
//                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED) // 一般用 STARTED
//                // .distinctUntilChanged() // 防抖,过滤掉所有相同值的后续重复. StateFlow 内部默认应用了该操作符，不能主动应用它;sharedFlow 可以
//                .catch { } // exception
//                .collect {
//                    when (it) {
//                        is MainUiState.Loading -> {
//                            logi("show loading view")
//                        }
//                        is MainUiState.LoadSuccess -> {
//                            logi("rv show data, add data of page ${it.page}, dismiss loading view")
//                            if (it.page == 1) { // refresh action
//                                mAdapter.data.clear()
//                            }
//                            mAdapter.data.addAll(it.data)
//                            mAdapter.notifyDataSetChanged()
//                        }
//                        is MainUiState.LoadError -> {
//                            if (mPage > 0) mPage--
//                            logi("show error view tips, error message is \"${it.error.message}\"")
//                        }
//                    }
//                }

        }

        // val flowTest =  MutableSharedFlow<Int>()
        // lifecycleScope.launchWhenCreated {
        //     flowTest.onStart {
        //         logi("onStart")
        //         emit(909999)
        //     }.flowOn(Dispatchers.IO).collect {
        //         logi("1-collect: $it")
        //     }
        //
        //     flowTest.collect {
        //         logi("2-collect: $it")
        //     }
        // }
        view?.findViewById<Button>(R.id.btn_refresh)?.setOnClickListener { // mViewModel.refresh()   以前 就是在 view 中使用 vm, 直接调用 vm 的具体 操作数据的函数
            mPage = 1
            mViewModel.dispatch(MainIntent.Refresh())

            // lifecycleScope.launchWhenCreated {
            //     flowTest.emit(1 + Random.nextInt(1..10))
            // }
        }

        view?.findViewById<Button>(R.id.btn_load_page)?.setOnClickListener {
            mPage++
            mViewModel.dispatch(MainIntent.LoadPageData(mPage, 5))
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mvi
    }
}