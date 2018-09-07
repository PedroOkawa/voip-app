package com.okawa.voip.utils.adapter

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> :  RecyclerView.Adapter<VH>() {

    private val data = ArrayList<T>()

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: Collection<T>?) {
        data?:return
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun add(item: T, position: Int) {
        data[position] = item
        notifyItemInserted(position)
    }

    fun getItem(position: Int): T {
        return data[position]
    }

    fun getItemPosition(item: T): Int {
        return data.indexOf(item)
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(item: T) {
        data.remove(item)
        notifyItemRemoved(getItemPosition(item))
    }

    fun reset() {
        data.clear()
        notifyDataSetChanged()
    }

    fun move(oldPosition: Int, newPosition: Int) {
        swap(data, oldPosition, newPosition)
    }

    private fun swap(data: ArrayList<T>, oldPosition: Int, newPosition: Int) {
        val item = data.removeAt(oldPosition)
        add(item, newPosition)
    }

}