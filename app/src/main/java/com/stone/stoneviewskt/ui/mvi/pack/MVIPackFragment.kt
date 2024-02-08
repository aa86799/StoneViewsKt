package com.stone.stoneviewskt.ui.mvi.pack

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.mvi.BaseMviFragment
import com.stone.stoneviewskt.common.mvi.InitDataIntent
import com.stone.stoneviewskt.common.mvi.LoadErrorState
import com.stone.stoneviewskt.common.mvi.LoadSuccessPageDataState
import com.stone.stoneviewskt.databinding.FragmentMviBinding
import com.stone.stoneviewskt.ui.mvi.data.MviData
import com.stone.stoneviewskt.ui.mvi.easy.MviDataAdapter
import com.stone.stoneviewskt.util.logi
import com.stone.stoneviewskt.util.stoneToast

/**
 * desc:
 * PackDatasource 中 模拟了 发生错误，抛出异常的代码
 * 在当前界面就会有个 不正常的现象，如：已加载 n>1 页的数据后， 点击刷新数据，这时 mPage=1，刷新时发生了异常，
 *      数据没有更新，还是 原来的 n 页数据；再点击加载下一页，就会将 mPage++；看起来可能数据反而更少了；
 *   所以，增加一个 mLastPage 参数
 *
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/17
 */
class MVIPackFragment : BaseMviFragment<FragmentMviBinding>(R.layout.fragment_mvi) {

    @PublishedApi
    internal val mViewModel by viewModels<MviPackViewModel>()
    private lateinit var mAdapter: MviDataAdapter
    private var mPage = 0
    private var mLastPage = mPage

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = MviDataAdapter()
        mBind.rvData.adapter = mAdapter

        view?.findViewById<Button>(R.id.btn_refresh)?.setOnClickListener {
            mPage = 1
            mViewModel.dispatch(PackIntent.Refresh())
        }

        view?.findViewById<Button>(R.id.btn_load_page)?.setOnClickListener {
            mPage++
            logi("mPage = $mPage")
            mViewModel.dispatch(PackIntent.LoadPageData(mPage))
        }
    }

    override fun initObserver() {
        // 订阅状态
        lifecycleScope.launchWhenCreated {
            stateFlowHandle(mViewModel.uiStateFlow, false) {
                if (it is LoadSuccessPageDataState<*>) {
                    when (it.subState) {
                        is PackUiState.RefreshPageDataSuccess -> {
                            mLastPage = mPage
                            logi("rv show data, refresh page ${it.page}")
                            mAdapter.updateData(it.data as ArrayList<MviData>?)
                        }
                        is PackUiState.LoadPageDataSuccess -> {
                            mLastPage = mPage
                            mAdapter.addAll(it.data as ArrayList<MviData>?)
                            logi("rv show data, add data of page ${it.page}")
                        }
                    }
                }
                if (it is LoadErrorState) {
                    mPage = mLastPage
//                    if (mPage > 0) mPage--
                    stoneToast("show error view tips, error message is \"${it.error}\"  mPage = $mPage")
                    logi("show error view tips, error message is \"${it.error}\"  mPage = $mPage")
                }
            }
        }
    }

    override fun initBiz() {
        mViewModel.dispatch(InitDataIntent())
    }
}