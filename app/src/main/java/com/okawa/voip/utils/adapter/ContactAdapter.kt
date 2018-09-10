package com.okawa.voip.utils.adapter

import android.database.Cursor
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterContactBinding
import com.okawa.voip.model.Contact
import com.okawa.voip.utils.mapper.ContactMapper

class ContactAdapter(private val contactMapper: ContactMapper) : CursorBindingAdapter<Contact, AdapterContactBinding>() {

    companion object {
        private const val DEFAULT_ACTIONS_POSITION = -1

        const val ACTION_TYPE_CALL = 0x0000
        const val ACTION_TYPE_DETAILS = 0x0001
    }

    private var listener: ((actionType: Int, Contact?) -> Unit)? = null

    private var showActionsPosition = DEFAULT_ACTIONS_POSITION

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_contact

    override fun convert(cursor: Cursor?): Contact? {
        return contactMapper.convert(cursor ?: return null)
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterContactBinding>, item: Contact?, position: Int) {
        holder.dataBinding?.txtContactName?.text = item?.name
        holder.dataBinding?.txtContactNumber?.text = item?.number
        holder.dataBinding?.image = item?.photo
        holder.dataBinding?.status = item?.isVoIPApp
        holder.dataBinding?.actions = showActions(position)
        holder.dataBinding?.btnContactCall?.setOnClickListener {
            listener?.invoke(ACTION_TYPE_CALL, item)
        }
        holder.dataBinding?.btnContactDetails?.setOnClickListener {
            listener?.invoke(ACTION_TYPE_DETAILS, item)
        }
        holder.itemView.setOnClickListener {
            defineOptions(position)
            holder.dataBinding?.actions = showActions(position)
        }
    }

    fun defineTouchListener(listener: (actionType: Int, Contact?) -> Unit) {
        this.listener = listener
    }

    private fun showActions(position: Int) = showActionsPosition == position

    private fun defineOptions(position: Int) {
        val previousPosition = showActionsPosition

        showActionsPosition = if(showActionsPosition == position) DEFAULT_ACTIONS_POSITION else position

        if(showActionsPosition != previousPosition) {
            notifyItemChanged(previousPosition)
        }
    }
}