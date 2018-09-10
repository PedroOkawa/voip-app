package com.okawa.voip.repository.contacts

import android.net.Uri

interface ContactsRepository {

    fun insertContact(name: String, number: String, photo: Uri?)

}