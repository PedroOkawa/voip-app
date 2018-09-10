package com.okawa.voip.repository.contacts

import android.net.Uri
import com.okawa.voip.db.DatabaseManager
import com.okawa.voip.model.Contact
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(private val databaseManager: DatabaseManager) : ContactsRepository {

    override fun insertContact(name: String, number: String, photo: Uri?) {
        databaseManager.insertContact(name, number, photo)
    }

}