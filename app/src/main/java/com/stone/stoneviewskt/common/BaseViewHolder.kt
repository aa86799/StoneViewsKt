package com.stone.stoneviewskt.common

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

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
