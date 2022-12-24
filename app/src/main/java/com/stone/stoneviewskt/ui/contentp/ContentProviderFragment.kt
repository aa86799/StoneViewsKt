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
class ContentProviderFragment : BaseBindFragment<FragmentCphBinding>(R.layout.fragment_cph) {

    private val mAdapter by lazy { DataResultAdapter(arrayListOf()) }

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        mBind.rvResult.adapter = mAdapter

        mBind.btnAddressQueryAll.debounceClick(this) {
            addressAll()
        }

        mBind.btnAddressQueryWhere.debounceClick(this) {
            addressWhere()
        }

        mBind.btnAddressFuzzyQuery.debounceClick(this) {
            addressFuzzyAll()
        }

        mBind.btnQueryAddressItem.debounceClick(this) {
            addressItem()
        }

        mBind.btnQueryAddressAdd.debounceClick(this) {
            addressAdd()
        }

        mBind.btnAddressDel.debounceClick(this) {
            addressDelete()
        }

        mBind.btnAddressUpdate.debounceClick(this) {
            addressUpdate()
        }
    }

    private fun addressAll() {
        mAdapter.removeAll()
        val projection = arrayOf("*")// 如果为 null，在query 中，会查出所有列
//        val projection = null // 如果为 null，在query 中，会查出所有列
        val selection = null
        val selectionArgs = null
        val order = "id asc"
        addressQuery("${MyContentProvider.contentUriStr}/address/all".toUri(), projection, selection,  selectionArgs, order)
    }

    private fun addressWhere() {
        mAdapter.removeAll()
        val projection = arrayOf("add_name", "phone", "address", "id")// 如果为 null，在query 中，会查出所有列
        val selection = "add_name=?" // 相当于 sql where 子句，不含 where 本身
        val selectionArgs = arrayOf("stone")
        val order = "id desc"
        addressQuery("${MyContentProvider.contentUriStr}/address/all".toUri(), projection, selection,  selectionArgs, order)
    }

    // 模糊查询 like 和 精确查询 = ，相结合
    private fun addressFuzzyAll() {
        mAdapter.removeAll()
        val projection = arrayOf("add_name", "phone", "address", "id")// 如果为 null，在query 中，会查出所有列
        val selection = "add_name like '%stone%' and phone=?" // 相当于 sql where 子句，不含 where 本身。like 后面的值直接写在这，不能写到 selectionArgs 数组中。
        val selectionArgs = arrayOf("phone-557")
        val order = "id desc"
        addressQuery("${MyContentProvider.contentUriStr}/address/all".toUri(), projection, selection,  selectionArgs, order)
    }

    // 查询一条记录
    private fun addressItem() {
        mAdapter.removeAll()
        val row = 2011193129
        val projection = arrayOf("*")
        val selection = "rowid=?" // 相当于 sql where 子句，不含 where 本身。like 后面的值直接写在这，不能写到 selectionArgs 数组中。
        val selectionArgs = arrayOf(row.toString())
        val order = "id desc"
        addressQuery("${MyContentProvider.contentUriStr}/address/item/$row".toUri(), projection, selection,  selectionArgs, order)
    }

    private fun addressQuery(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, order: String?) {
        val resolver = requireActivity().contentResolver
        resolver.query(uri, projection, selection,  selectionArgs, order)
            ?.use { cursor ->
                cursor.moveToFirst()
                repeat(cursor.count) { // 这里用repeat来代替foreach，是因为循环中的 it 指代对象 在后续用不到
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
//        values.put("add_name", "add_name-$random")
        values.put("add_name", "stone-$random")
        values.put("phone", "phone-$random")
        values.put("address", "address-$random")
        val resolver = requireActivity().contentResolver
        resolver.insert(Uri.parse("${MyContentProvider.contentUriStr}/address/item/add"), values)
    }

    private fun addressDelete() {
        val where = "add_name=?"
        val selectionArgs = arrayOf("add_name-580")
        val resolver = requireActivity().contentResolver
        resolver.delete("${MyContentProvider.contentUriStr}/address/item/del".toUri(), where, selectionArgs)
        addressAll()
    }

    private fun addressUpdate() {
        val where = "add_name=?"
        val selectionArgs = arrayOf("add_name-882")
        val values = ContentValues()
        values.put("phone", "phone-1998")
        val resolver = requireActivity().contentResolver
        resolver.update("${MyContentProvider.contentUriStr}/address/item/update".toUri(), values, where, selectionArgs)
        addressAll()
    }
}