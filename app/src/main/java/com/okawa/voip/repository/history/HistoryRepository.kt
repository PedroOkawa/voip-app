package com.okawa.voip.repository.history

import android.database.Cursor
import com.okawa.voip.model.History

interface HistoryRepository {

    fun retrieveHistory(cursor: Cursor?, listener: (List<History>) -> Unit)

}