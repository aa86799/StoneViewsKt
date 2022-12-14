package com.stone.stoneviewskt.ui.contentp

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.common.debounceClick
import com.stone.stoneviewskt.databinding.FragmentCphBinding
import com.stone.stoneviewskt.util.logi
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/12/11 15:44
 */
class ContentProviderHomeFragment : BaseBindFragment<FragmentCphBinding>(R.layout.fragment_cph) {

    private val mAdapter by lazy { DataResultAdapter(arrayListOf()) }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.rvResult.adapter = mAdapter

        mBind.btnQueryAddressAll.debounceClick(this) {
            addressAll()
        }

        mBind.btnQueryAddressAdd.debounceClick(this) {
            addressAdd()
        }
    }

    private fun addressAll() {
        val resolver = requireActivity().contentResolver
        val projection = arrayOf("add_name", "phone", "address", "id")// 如果为 null，在query 中，会查出所有列
        val selection = "add_name=?" // 相当于 sql where 子句，不含 where 本身
        val selectionArgs = arrayOf("stone")
        val order = "id desc"
        resolver.query("${MyContentProvider.contentUriStr}/address/all".toUri(), projection, selection,  selectionArgs, order, null)
            ?.use { cursor ->
//                repeat((0 until cursor.count).count()) {
                cursor.moveToFirst()
                repeat(cursor.count) {
                    val name = cursor.getString(cursor.getColumnIndex("add_name"))
                    val phone = cursor.getString(cursor.getColumnIndex("phone"))
                    val address = cursor.getString(cursor.getColumnIndex("address"))
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    logi("$id $name $phone $address")
                    mAdapter.addItem("$id $name $phone $address")
                    cursor.moveToNext()
                }
            }
    }

    private fun addressAdd() {
        val random = Random.nextInt(0, 1000)
        val values = ContentValues()
        values.put("add_name", "add_name-$random")
        values.put("phone", "phone-$random")
        values.put("address", "address-$random")
        val resolver = requireActivity().contentResolver
        resolver.insert(Uri.parse("${MyContentProvider.contentUriStr}/address/item/add"), values)
    }
}