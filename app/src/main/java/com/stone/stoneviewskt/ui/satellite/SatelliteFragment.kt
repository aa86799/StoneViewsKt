package com.stone.stoneviewskt.ui.satellite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import com.stone.stoneviewskt.util.showShort
import kotlinx.android.synthetic.main.fragment_satellite.*

/**
 * desc:    卫星式菜单
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 15:12
 */
class SatelliteFragment: BaseFragment() {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        fragment_satellite_sm_menu.setOnMenuItemClickListener { v, position ->
            showShort("${v.tag}(position $position) is click")
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_satellite
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_satellite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_leftTop -> {
                fragment_satellite_sm_menu.setPosition(SatelliteMenuView.Position.POS_LEFT_TOP)
                return true
            }

            R.id.action_rightTop -> {
                fragment_satellite_sm_menu.setPosition(SatelliteMenuView.Position.POS_RIGHT_TOP)
                return true
            }

            R.id.action_leftBottom -> {
                fragment_satellite_sm_menu.setPosition(SatelliteMenuView.Position.POS_LEFT_BOTTOM)
                return true
            }

            R.id.action_rightBottom -> {
                fragment_satellite_sm_menu.setPosition(SatelliteMenuView.Position.POS_RIGHT_BOTTOM)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}