package com.okawa.voip.utils.mapper

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.okawa.voip.db.DatabaseHelper
import com.okawa.voip.model.Contact
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactMapper @Inject constructor() {

    /**
     * Converts the cursor to a Contact instance
     *
     * @param cursor holding the contact data
     *
     * @return contact instance
     */
    fun convert(cursor: Cursor): Contact {
        val contactIdColumnId = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
        val nameColumnId = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
        val numberColumnId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val photoUriColumnId = cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI)
        val photoColumnId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO)
        val mimeTypeColumnId = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)

        val contactId = cursor.getString(contactIdColumnId)
        val name = cursor.getString(nameColumnId) ?: "Unknown"
        val number = cursor.getString(numberColumnId) ?: "Unknown"
        val photoString = cursor.getString(photoUriColumnId)
        val photoUri = Uri.parse(photoString ?: "")
        val mimeType = cursor.getString(mimeTypeColumnId)
        val test = cursor.getString(photoColumnId)
        val isVoIPApp = mimeType == DatabaseHelper.MIME_TYPE

        return Contact(contactId, name, number, photoUri, isVoIPApp)
    }

    fun convert(name: String, number: String, photo: Uri?, isVoIPApp: Boolean) = Contact("0", name, number, photo, isVoIPApp)

}