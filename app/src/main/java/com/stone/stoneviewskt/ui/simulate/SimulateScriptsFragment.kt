package com.stone.stoneviewskt.ui.simulate

import android.os.Bundle
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentSimulateScriptsBinding

class SimulateScriptsFragment : BaseBindFragment<FragmentSimulateScriptsBinding>(R.layout.fragment_simulate_scripts) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        activity ?: return

    }
}