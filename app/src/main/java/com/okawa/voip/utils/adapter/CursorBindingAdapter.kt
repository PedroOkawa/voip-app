package com.okawa.voip.utils.adapter

import android.database.Cursor
import android.databinding.ViewDataBinding

abstract class CursorBindingAdapter<T, K : ViewDataBinding> : BaseBindingAdapter<T, K>() {

    private var cursor: Cursor? = null

    abstract fun convert(cursor: Cursor?): T?

    override fun onBindViewHolder(holder: BindingViewHolder<K>, position: Int) {
        if(cursor?.moveToPosition(position) == true) {
            doOnBindViewHolder(holder, convert(cursor), position)
        }
    }

    fun setCursor(cursor: Cursor?) {
        this.cursor?.close()
        this.cursor = cursor
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(cursor == null || (cursor?.isClosed == true)) 0 else cursor?.count ?: 0
    }
}