package com.okawa.voip.utils.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseBindingAdapter<T, K : ViewDataBinding> : BaseAdapter<T, BaseBindingAdapter.BindingViewHolder<K>>() {

    @LayoutRes
    abstract fun layoutToInflate(viewType: Int): Int

    abstract fun doOnBindViewHolder(holder: BindingViewHolder<K>, item: T?, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<K> {
        val view = LayoutInflater.from(parent.context).inflate(layoutToInflate(viewType), parent, false)
        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<K>, position: Int) {
        doOnBindViewHolder(holder, getItem(position), position)
    }

    class BindingViewHolder<K : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {

        val dataBinding: K? = DataBindingUtil.bind(view)

    }

}