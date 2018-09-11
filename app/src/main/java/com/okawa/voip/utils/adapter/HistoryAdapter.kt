package com.okawa.voip.utils.adapter

import android.database.Cursor
import com.okawa.voip.R
import com.okawa.voip.databinding.AdapterHistoryBinding
import com.okawa.voip.model.History
import com.okawa.voip.utils.mapper.HistoryMapper
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val historyMapper: HistoryMapper) : ContactActionsAdapter<History, AdapterHistoryBinding>() {

    companion object {
        const val DATE_FORMAT = "HH:mm - dd MMM"
    }

    override fun layoutToInflate(viewType: Int) = R.layout.adapter_history

    override fun convert(cursor: Cursor?): History? {
        return historyMapper.convert(cursor ?: return null)
    }

    override fun doOnBindViewHolder(holder: BindingViewHolder<AdapterHistoryBinding>, item: History?, position: Int) {
        holder.dataBinding?.let { binding ->
            binding.txtHistoryName.text = item?.name
            binding.txtHistoryNumber.text = item?.number
            binding.txtHistoryDate.text = convertDate(item?.date)
            binding.image = item?.photo
            binding.status = item?.isVoIPApp
            binding.hideActions = hideActions(position)
            binding.hideEdit = item?.isVoIPApp == false

            binding.btnHistoryCall.setOnClickListener {
                onActionCallClicked(item)
            }


            binding.btnHistoryEdit.setOnClickListener {
                onActionEditClicked(item)
            }

            binding.root.setOnClickListener {
                defineActionsPosition(position)
                binding.hideActions = hideActions(position)
            }
        }
    }

    /**
     * Converts the date to a displayable value (Hour : Minutes - Day Month)
     *
     * @param date call log date
     *
     * @return Displayable date
     */
    private fun convertDate(date: Date?): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)
    }
}