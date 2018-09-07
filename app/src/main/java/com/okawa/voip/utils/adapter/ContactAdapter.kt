package com.okawa.voip.utils.adapter

import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterContactBinding
import com.okawa.voip.model.Contact

class ContactAdapter : BaseBindingAdapter<Contact, AdapterContactBinding>() {

    private var cursor: Cursor? = null

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_contact

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, item: Contact?, position: Int) {
        holder.dataBinding?.txtContactName?.text = item?.name

        val photo = item?.photo ?: ""
        Log.w("TEST", "PHOTO: $photo")
        Glide.with(holder.itemView)
                .load(photo)
                .apply(RequestOptions().placeholder(R.drawable.ic_contact_placeholder))
                .into(holder.dataBinding?.imgContactPhoto!!)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, position: Int) {
        if(cursor?.moveToPosition(position) == true) {
            val name = cursor?.getString(0) ?: ""
            val photoBlob = cursor?.getBlob(1)
            val photo = photoBlob?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size ?: 0)
            } ?: null
            /*
            val photo = photoBlob?.let {
                val photoString = String(it, charset("UTF-8"))
                return@let Uri.parse(photoString)
            } ?: Uri.parse("")
            */
            val contact = Contact(name, photo)
            doOnBindViewHolder(holder, contact, position)
        }
    }

    fun setData(cursor: Cursor?) {
        this.cursor = cursor
        notifyDataSetChanged()
    }
}