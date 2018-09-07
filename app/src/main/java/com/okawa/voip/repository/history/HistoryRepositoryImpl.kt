package com.okawa.voip.repository.history

import android.database.Cursor
import com.okawa.voip.model.History
import com.okawa.voip.utils.executors.AppExecutors
import com.okawa.voip.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val appExecutors: AppExecutors, private val historyMapper: HistoryMapper) : HistoryRepository {

    override fun retrieveHistory(cursor: Cursor?, listener: (List<History>) -> Unit) {
        cursor?.let {
            appExecutors.getDiskIO().execute {
                val result = ArrayList<History>()

                while (!it.isClosed && it.moveToNext()) {
                    result.add(historyMapper.convert(it))
                }

                listener.invoke(result)
            }
        }
    }

}