package com.okawa.voip.repository.contacts

import android.net.Uri

interface ContactsRepository {

    fun insertContact(name: String, number: String, photo: Uri?)

    fun updateContact(id: String, name: String, photo: Uri?)

    fun deleteContact(id: String)

}