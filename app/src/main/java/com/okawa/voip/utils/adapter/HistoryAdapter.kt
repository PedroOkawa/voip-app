package com.okawa.voip.utils.adapter

import android.database.Cursor
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterHistoryBinding
import com.okawa.voip.model.History
import com.okawa.voip.utils.mapper.HistoryMapper
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val historyMapper: HistoryMapper) : CursorBindingAdapter<History, AdapterHistoryBinding>() {

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_history

    override fun convert(cursor: Cursor?): History? {
        return historyMapper.convert(cursor ?: return null)
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterHistoryBinding>, item: History?, position: Int) {
        holder.dataBinding?.txtHistoryName?.text = item?.name
        holder.dataBinding?.txtHistoryNumber?.text = item?.number
        holder.dataBinding?.txtHistoryDate?.text = convertDate(item?.date)
        holder.dataBinding?.image = item?.photo
        holder.dataBinding?.status = item?.isVoIPApp
        holder.itemView.setOnClickListener {

        }
    }

    private fun convertDate(date: Date?): String {
        return SimpleDateFormat("dd MMM - HH:mm", Locale.getDefault()).format(date)
    }
}