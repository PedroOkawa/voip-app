package com.okawa.voip.repository.history

import com.okawa.voip.db.DatabaseManager
import com.okawa.voip.model.Contact
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val databaseManager: DatabaseManager) : HistoryRepository {

    override fun insertHistory(contact: Contact) {
        databaseManager.insertHistory(contact)
    }

}