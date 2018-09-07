package com.okawa.voip.utils.adapter

import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterHistoryBinding
import com.okawa.voip.model.History

class HistoryAdapter : BaseBindingAdapter<History, AdapterHistoryBinding>() {

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_history

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterHistoryBinding>, item: History?, position: Int) {
        holder.dataBinding?.txtHistoryName?.text = item?.name
        holder.dataBinding?.txtHistoryNumber?.text = item?.number
        holder.dataBinding?.txtHistoryDate?.text = item?.time
        holder.dataBinding?.placeholder = R.drawable.ic_contact_placeholder
        holder.dataBinding?.image = item?.photo
    }
}