package com.okawa.voip.repository.history

import com.okawa.voip.db.DatabaseManager
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import com.okawa.voip.utils.mapper.HistoryMapper
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val databaseManager: DatabaseManager, private val historyMapper: HistoryMapper) : HistoryRepository {

    override fun insertHistory(contact: Contact) {
        databaseManager.insertHistory(historyMapper.convert(contact))
    }

    override fun insertHistory(history: History) {
        databaseManager.insertHistory(history)
    }

    override fun deleteHistory() {
        databaseManager.deleteVoIPAppHistory()
    }

}
