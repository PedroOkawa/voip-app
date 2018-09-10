package com.okawa.voip.utils.adapter

import android.content.Context
import android.widget.ArrayAdapter

open class SingleArrayAdapter<T>(context: Context, resource: Int) : ArrayAdapter<T>(context, resource, ArrayList()) {

    override fun getCount(): Int {
        return if(super.getCount() == 0) 0 else 1
    }

}