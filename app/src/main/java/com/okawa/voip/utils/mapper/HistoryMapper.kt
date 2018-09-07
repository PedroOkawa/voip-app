package com.okawa.voip.utils.mapper

import android.database.Cursor
import android.provider.CallLog
import com.okawa.voip.model.History
import javax.inject.Inject

class HistoryMapper @Inject constructor() {

    /**
     * Converts the cursor to a Contact instance
     *
     * @param cursor holding the contact data
     *
     * @return contact instance
     */
    fun convert(cursor: Cursor): History {
        val nameColumnId = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val numberColumnId = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        //val photoColumnId = cursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO)
        val dateColumnId = cursor.getColumnIndex(CallLog.Calls.DATE)

        val name = cursor.getString(nameColumnId) ?: "Unknown"
        val number = cursor.getString(numberColumnId) ?: "Unknown"
        //val photoData: ByteArray? = cursor.getBlob(photoColumnId)
        //val photoBitmap = if(photoData != null) BitmapFactory.decodeByteArray(photoData, 0, photoData.size) else null
        val date = cursor.getString(dateColumnId) ?: ""

        return History(name, number, null, date)
    }

}