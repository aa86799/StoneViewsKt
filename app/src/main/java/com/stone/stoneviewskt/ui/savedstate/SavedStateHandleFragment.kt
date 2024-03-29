package com.stone.stoneviewskt.ui.savedstate

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentSavedStateBinding

class SavedStateHandleFragment : BaseBindFragment<FragmentSavedStateBinding>(R.layout.fragment_saved_state) {

    // 这种实现，之前的瞬时数据还在，后面两种实现，基于 fragment 生命周期的，则不能持久化
    private val vm1: SViewModel1 by activityViewModels()
//    private val vm1: SViewModel1 by viewModels()
//    private val vm1: SViewModel1 by viewModels {
//        SavedStateViewModelFactory(StoneApplication.instance, this)
//    }


    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)
        /*
         * androidx.lifecycle.SavedStateHandle
         *  用于在配置更改（如设备旋转）或进程死亡并重新创建时，保存和恢复 UI 状态。
         *  它以键值对(key-value map) 的形式存储数据，开发者可以通过键来保存和检索状态信息。
         *  简化状态管理：使用 SavedStateHandle，开发者不需要手动处理 onSaveInstanceState() 和
         *      onRestoreInstanceState()，简化了状态管理的复杂性。
         *  与 ViewModel 集成：SavedStateHandle 通常与 ViewModel 结合使用：
         *      当创建 ViewModel 时，可以通过 ViewModel 构造函数传递 SavedStateHandle 实例，
         *      从而使 ViewModel 能够访问之前保存的状态。
         */

        mBind.etVm1.doAfterTextChanged {
            vm1.saveState(it.toString())
        }

        vm1.userInput.observe(this) {
            if (it == mBind.etVm1.text.toString()) return@observe
            mBind.etVm1.setText(it)
        }
    }

    // 要求 所属的 activity，在 manifest 中配置了 android:configChanges="..."
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        vm1.getState()
    }
}