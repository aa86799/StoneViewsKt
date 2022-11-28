package com.stone.stoneviewskt.common

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

// 若定义成 BaseViewHolder(val itemView: View) 会有inflate 为null 的 bug
@Suppress("UNCHECKED_CAST")
class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> findView(@IdRes resourceId: Int): T {
        var mView = mViews.get(resourceId)
        if (mView == null) {
            mView = itemView.findViewById<T>(resourceId)
            mViews.put(resourceId, mView)
        }
        return mView as T
    }
}
