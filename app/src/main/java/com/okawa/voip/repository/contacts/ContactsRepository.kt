package com.okawa.voip.repository.contacts

import android.database.Cursor
import com.okawa.voip.model.Contact

interface ContactsRepository {

    fun retrieveContacts(cursor: Cursor?, listener: (List<Contact>) -> Unit)

}