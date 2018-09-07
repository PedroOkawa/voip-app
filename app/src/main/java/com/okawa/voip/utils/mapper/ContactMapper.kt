package com.okawa.voip.utils.mapper

import android.database.Cursor
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import com.okawa.voip.model.Contact
import javax.inject.Inject

class ContactMapper @Inject constructor() {

    /**
     * Converts the cursor to a Contact instance
     *
     * @param cursor holding the contact data
     *
     * @return contact instance
     */
    fun convert(cursor: Cursor): Contact {
        val nameColumnId = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
        val photoColumnId = cursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO)

        val name = cursor.getString(nameColumnId) ?: "Unknown"
        val photoData: ByteArray? = cursor.getBlob(photoColumnId)
        val photoBitmap = if(photoData != null) BitmapFactory.decodeByteArray(photoData, 0, photoData.size) else null

        return Contact(name, photoBitmap)
    }

}