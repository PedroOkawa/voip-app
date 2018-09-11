package com.okawa.voip.repository.contacts

import android.net.Uri
import com.okawa.voip.db.DatabaseManager
import com.okawa.voip.model.Contact
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(private val databaseManager: DatabaseManager) : ContactsRepository {

    override fun insertContact(name: String, number: String, photo: Uri?) {
        databaseManager.insertContact(name, number, photo)
    }

    override fun updateContact(id: String, name: String, photo: Uri?) {
        databaseManager.updateContact(id, name, photo)
    }

    override fun deleteContact(id: String) {
        databaseManager.deleteContact(id)
    }

    override fun deleteAllData() {
        databaseManager.deleteAllData()
    }

}