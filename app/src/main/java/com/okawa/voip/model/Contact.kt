package com.okawa.voip.model

import android.net.Uri
import android.provider.ContactsContract
import com.okawa.voip.db.DatabaseHelper

data class Contact(val id: String, val name: String, val number: String, val photo: Uri?, val isVoIPApp: Boolean) {

    companion object {

        val CONTENT_URI: Uri = ContactsContract.Data.CONTENT_URI

        val PROJECTION = arrayOf(
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Data.PHOTO_URI,
                ContactsContract.Data.MIMETYPE
        )

        val SELECTION_ALL_CLAUSE = "${ContactsContract.CommonDataKinds.Phone.MIMETYPE} IN (?, ?)"

        val SELECTION_ALL_ARGUMENTS = arrayOf(DatabaseHelper.MIME_TYPE, "vnd.android.cursor.item/phone_v2")

        val SELECTION_VOIP_APP_CLAUSE = "${ContactsContract.CommonDataKinds.Phone.MIMETYPE} = ?"

        val SELECTION_VOIP_APP_ARGUMENTS = arrayOf(DatabaseHelper.MIME_TYPE)

        const val SORT_ORDER = ContactsContract.Data.DISPLAY_NAME
    }

}