package com.okawa.voip.utils

import android.provider.ContactsContract

object CursorUtils {
    val PROJECTION = arrayOf(
            ContactsContract.Contacts.Data._ID,
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.Contacts.Data.DATA1,
            ContactsContract.Contacts.Data.DATA2,
            ContactsContract.Contacts.Data.DATA3,
            ContactsContract.Contacts.Data.DATA4,
            ContactsContract.Contacts.Data.DATA5,
            ContactsContract.Contacts.Data.DATA6,
            ContactsContract.Contacts.Data.DATA7,
            ContactsContract.Contacts.Data.DATA8,
            ContactsContract.Contacts.Data.DATA9,
            ContactsContract.Contacts.Data.DATA10,
            ContactsContract.Contacts.Data.DATA11,
            ContactsContract.Contacts.Data.DATA12,
            ContactsContract.Contacts.Data.DATA13,
            ContactsContract.Contacts.Data.DATA14,
            ContactsContract.Contacts.Data.DATA15
    )

    val SELECTION_ARGUMENTS = arrayOf("")

    const val SELECTION = "${ContactsContract.Data.LOOKUP_KEY} = ?"

    const val SORT_ORDER = ContactsContract.Data.MIMETYPE

}