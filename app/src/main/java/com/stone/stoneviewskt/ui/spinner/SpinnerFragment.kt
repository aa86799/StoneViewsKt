package com.stone.stoneviewskt.ui.spinner

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_spinner.*

/**
 * desc:    Spinner 测试
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/28 20:56
 */
class SpinnerFragment: BaseFragment() {

    var mClick = false

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        /*
         * 对 Spinner 的 静态展示的第一行做了某些操作(text="" 或 隐藏view)后，
         * 在展开的列表中直接选择第一行，是无效的。 这是系统的bug？
         * 所以，通常要么 不对第一行做特殊处理； 要么 加上一个说明项的数据
         */

        val list = mutableListOf("abc", "cdf", "ggg")
//        list.add(0, "请选择")
        fragment_spinner_sp.adapter = getSpAdapter(list, true) {
            list[it]
        }
//        fragment_spinner_sp.post {
//            val firstChild = fragment_spinner_sp?.getChildAt(0) as? CheckedTextView
//            firstChild?.text = ""
//        }


        fragment_spinner_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mClick = true
                view?.postInvalidate()
            }

        }
    }

    private fun <T> getSpAdapter(listData: List<T>, isFirstBackGray: Boolean, itemText: ((itemPosition: Int) -> String)): ArrayAdapter<T> {
        return object : ArrayAdapter<T>(mActivity, android.R.layout.simple_spinner_dropdown_item, listData) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {//静态展示的
                val view = super.getView(position, convertView, parent)
                val tv = view.findViewById<TextView>(android.R.id.text1)
                tv.gravity = Gravity.CENTER
                tv.text = itemText(position)
                tv.textSize = 14f
                if (position == 0 && !mClick) {
                    tv.text = ""
                    tv.visibility = View.INVISIBLE
                    tv.setTextColor(Color.RED)
                } else {
                    tv.visibility = View.VISIBLE
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {//打开展示的
                val tv = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0 && isFirstBackGray) {
                    tv.setBackgroundColor(Color.GRAY)
                } else {
                    tv.setBackgroundColor(Color.WHITE)
                }
                return tv
            }
        }

    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_spinner
    }
}