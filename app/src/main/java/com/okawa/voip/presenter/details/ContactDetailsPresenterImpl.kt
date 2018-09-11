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

    private var contact: Contact? = null

    private var photo:Uri? = null

    override fun defineContact(contact: Contact?) {
        this.contact = contact
    }

    override fun definePhoto(photo: Uri?) {
        this.photo = photo
    }

    override fun getContact() = contact

    override fun isEditMode() = contact != null && contact?.id?.isEmpty() == false

    override fun generateRandomVoIPAppNumber() = "${generateRandomNumber()}$VOIP_APP_SUFFIX"

    override fun insertContact(name: String, number: String) {
        contactsRepository.insertContact(name, number, photo)
    }

    override fun updateContact(name: String) {
        contact?.id?.let {
            contactsRepository.updateContact(it, name, photo)
        }
    }

    override fun deleteContact() {
        contact?.id?.let {
            contactsRepository.deleteContact(it)
        }
    }

    /**
     * Generates random VoIP App number
     *
     * @return VoIP App number
     */
    private fun generateRandomNumber(): Int {
        val max = Math.pow(10.0, 9.0).toInt() - 1
        val min = Math.pow(10.0, 8.0).toInt()
        val range = max - min
        val random = Random()
        val x = random.nextInt(range)
        return x + min
    }

}