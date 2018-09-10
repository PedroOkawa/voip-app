package com.okawa.voip.utils.mapper

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import com.okawa.voip.utils.extensions.toBoolean
import com.okawa.voip.utils.extensions.toInt
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryMapper @Inject constructor() {

    /**
     * Converts the cursor to a History instance
     *
     * @param cursor holding the history data
     *
     * @return history instance
     */
    fun convert(cursor: Cursor): History {
        val nameColumnId = cursor.getColumnIndex(History.COLUMN_NAME)
        val numberColumnId = cursor.getColumnIndex(History.COLUMN_NUMBER)
        val photoColumnId = cursor.getColumnIndex(History.COLUMN_PHOTO)
        val isVoIPAppColumnId = cursor.getColumnIndex(History.COLUMN_IS_VOIP_APP)
        val dateColumnId = cursor.getColumnIndex(History.COLUMN_DATE)

        val name = cursor.getString(nameColumnId) ?: "Unknown"
        val number = cursor.getString(numberColumnId) ?: "Unknown"
        val photoString = cursor.getString(photoColumnId)
        val photo = Uri.parse(photoString ?: "")
        val isVoIPAppInt = cursor.getInt(isVoIPAppColumnId)
        val dateTime = cursor.getLong(dateColumnId)

        val date = Date()
        date.time = dateTime

        return History(name, number, photo, isVoIPAppInt.toBoolean(), date)
    }

    /**
     * Converts the contact to a History instance
     *
     * @param contact holding the history data
     *
     * @return history instance
     */
    fun convert(contact: Contact) = History(contact.name, contact.number, contact.photo, contact.isVoIPApp, Date())

}