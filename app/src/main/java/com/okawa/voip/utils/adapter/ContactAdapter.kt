package com.okawa.voip.utils.adapter

import android.database.Cursor
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterContactBinding
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.mapper.ContactMapper

class ContactAdapter(private val contactMapper: ContactMapper) : ContactActionsAdapter<Contact, AdapterContactBinding>() {

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_contact

    override fun convert(cursor: Cursor?): Contact? {
        return contactMapper.convert(cursor ?: return null)
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, item: Contact?, position: Int) {
        holder.dataBinding?.let { binding ->
            binding.txtContactName.text = item?.name
            binding.txtContactNumber.text = item?.number
            binding.image = item?.photo
            binding.status = item?.isVoIPApp
            binding.hideActions = hideActions(position)
            binding.hideEdit = item?.isVoIPApp == false

            binding.btnContactCall.setOnClickListener {
                onActionCallClicked(item)
            }


            binding.btnContactEdit.setOnClickListener {
                onActionEditClicked(item)
            }

            binding.root.setOnClickListener {
                defineActionsPosition(position)
                binding.hideActions = hideActions(position)
            }
        }
    }
}