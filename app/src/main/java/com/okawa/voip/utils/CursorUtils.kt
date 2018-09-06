package com.okawa.voip.utils

import android.provider.ContactsContract

object CursorUtils {
    val PROJECTION = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1
    )

    val SELECTION_ARGUMENTS = arrayOf("")

    const val SELECTION = "${ContactsContract.Data.LOOKUP_KEY} = ?"

    const val SORT_ORDER = ContactsContract.Data.MIMETYPE

}