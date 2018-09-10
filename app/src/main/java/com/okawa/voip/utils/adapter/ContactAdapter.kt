package com.okawa.voip.utils.adapter

import android.database.Cursor
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterContactBinding
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.mapper.ContactMapper

class ContactAdapter(private val contactMapper: ContactMapper) : CursorBindingAdapter<Contact, AdapterContactBinding>() {

    private var listener: ((Contact?) -> Unit)? = null

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_contact

    override fun convert(cursor: Cursor?): Contact? {
        return contactMapper.convert(cursor ?: return null)
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, item: Contact?, position: Int) {
        holder.dataBinding?.txtContactName?.text = item?.name
        holder.dataBinding?.txtContactNumber?.text = item?.number
        holder.dataBinding?.image = item?.photo
        holder.dataBinding?.status = item?.isVoIPApp
        holder.itemView.setOnClickListener {
            listener?.invoke(item)
        }
    }

    fun defineTouchListener(listener: (Contact?) -> Unit) {
        this.listener = listener
    }
}