package com.okawa.voip.utils.mapper

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.okawa.voip.R
import com.okawa.voip.db.DatabaseHelper
import com.okawa.voip.model.Contact
import com.okawa.voip.model.History
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactMapper @Inject constructor(private val application: Application) {

    /**
     * Converts the cursor to a Contact instance
     *
     * @param cursor holding the contact data
     *
     * @return contact instance
     */
    fun convert(cursor: Cursor): Contact {
        val unknown = application.getString(R.string.common_unknown_name)
        val contactIdColumnId = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
        val nameColumnId = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
        val numberColumnId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val photoUriColumnId = cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI)
        val mimeTypeColumnId = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)

        val contactId = cursor.getString(contactIdColumnId)
        val name = cursor.getString(nameColumnId) ?: unknown
        val number = cursor.getString(numberColumnId) ?: unknown
        val photoString = cursor.getString(photoUriColumnId)
        val photoUri = Uri.parse(photoString ?: "")
        val mimeType = cursor.getString(mimeTypeColumnId)
        val isVoIPApp = mimeType == DatabaseHelper.MIME_TYPE

        return Contact(contactId, name, number, photoUri, isVoIPApp)
    }

    /**
     * Converts the history to a Contact
     *
     * @param history call
     *
     * @return contact instance
     */
    fun convert(history: History) = Contact(history.contactId, history.name, history.number, history.photo, history.isVoIPApp)

}