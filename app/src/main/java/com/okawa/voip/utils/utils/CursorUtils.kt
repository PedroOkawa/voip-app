package com.okawa.voip.utils.utils

import android.provider.CallLog
import android.provider.ContactsContract

object CursorUtils {

    object Contacts {
        val PROJECTION = arrayOf(
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.Contacts.Photo.PHOTO
        )

        val SELECTION_ARGUMENTS = null

        val SELECTION_CLAUSE = null

        const val SORT_ORDER = ContactsContract.Data.DISPLAY_NAME
    }

    object History {
        val PROJECTION = arrayOf(
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
        )

        val SELECTION_ARGUMENTS = null

        val SELECTION_CLAUSE = null

        const val SORT_ORDER = "${CallLog.Calls.DATE} DESC "
    }

}