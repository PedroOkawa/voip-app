package com.okawa.voip.presenter.details

import android.net.Uri
import com.okawa.voip.model.Contact
import com.okawa.voip.repository.contacts.ContactsRepository
import java.util.*
import javax.inject.Inject

class ContactDetailsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository) : ContactDetailsPresenter {

    companion object {

        private const val VOIP_APP_SUFFIX = "VoIPApp"
    }

    override fun generateRandomVoIPAppNumber() = "${generateRandomNumber()}$VOIP_APP_SUFFIX"

    override fun insertContact(name: String, number: String, photo: Uri?) {
        contactsRepository.insertContact(name, number, photo)
    }

    override fun updateContact(id: String?, name: String, photo: Uri?) {
        id?.let {
            contactsRepository.updateContact(id, name, photo)
        }
    }

    private fun generateRandomNumber(): Int {
        val max = Math.pow(10.0, 9.0).toInt() - 1
        val min = Math.pow(10.0, 8.0).toInt()
        val range = max - min
        val random = Random()
        val x = random.nextInt(range)
        return x + min
    }

}