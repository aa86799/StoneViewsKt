package com.stone.stoneviewskt.ui.satellite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentSatelliteBinding
import com.stone.stoneviewskt.util.showShort

/**
 * desc:    卫星式菜单
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/6 15:12
 */
class SatelliteFragment : BaseBindFragment<FragmentSatelliteBinding>(R.layout.fragment_satellite) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        mBind.fragmentSatelliteSmMenu.setOnMenuItemClickListener { v, position ->
            showShort("${v.tag}(position $position) is click")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_satellite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_leftTop -> {
                mBind.fragmentSatelliteSmMenu.setPosition(SatelliteMenuView.Position.POS_LEFT_TOP)
                return true
            }

            R.id.action_rightTop -> {
                mBind.fragmentSatelliteSmMenu.setPosition(SatelliteMenuView.Position.POS_RIGHT_TOP)
                return true
            }

            R.id.action_leftBottom -> {
                mBind.fragmentSatelliteSmMenu.setPosition(SatelliteMenuView.Position.POS_LEFT_BOTTOM)
                return true
            }

            R.id.action_rightBottom -> {
                mBind.fragmentSatelliteSmMenu.setPosition(SatelliteMenuView.Position.POS_RIGHT_BOTTOM)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}