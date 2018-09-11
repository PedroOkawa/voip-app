package com.okawa.voip.repository.history

import com.okawa.voip.model.Contact
import com.okawa.voip.model.History

interface HistoryRepository {

    fun insertHistory(contact: Contact)

    fun insertHistory(history: History)

    fun deleteHistory()

}