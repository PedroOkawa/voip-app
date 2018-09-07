package com.okawa.voip.utils.adapter

import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterContactBinding
import com.okawa.voip.model.Contact

class ContactAdapter : BaseBindingAdapter<Contact, AdapterContactBinding>() {

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_contact

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, item: Contact?, position: Int) {
        holder.dataBinding?.txtContactName?.text = item?.name
        holder.dataBinding?.placeholder = R.drawable.ic_contact_placeholder
        holder.dataBinding?.image = item?.photo
    }
}