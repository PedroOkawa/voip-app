package com.okawa.voip.repository.history

import com.okawa.voip.model.Contact

interface HistoryRepository {

    fun insertHistory(contact: Contact)

}