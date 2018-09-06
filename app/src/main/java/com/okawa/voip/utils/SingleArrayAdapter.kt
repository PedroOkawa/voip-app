package com.okawa.voip.utils

import android.content.Context
import android.widget.ArrayAdapter

open class SingleArrayAdapter<T>(context: Context, resource: Int, objects: List<T>) : ArrayAdapter<T>(context, resource, objects) {

    override fun getCount(): Int {
        return if(super.getCount() == 0) 0 else 1
    }

}