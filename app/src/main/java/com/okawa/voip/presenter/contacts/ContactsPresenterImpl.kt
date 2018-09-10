package com.okawa.voip.presenter.contacts

import com.okawa.voip.model.Contact
import com.okawa.voip.repository.contacts.ContactsRepository
import com.okawa.voip.repository.history.HistoryRepository
import javax.inject.Inject

class ContactsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository, private val historyRepository: HistoryRepository) : ContactsPresenter {

    override fun insertContact(contact: Contact?) {
        contactsRepository.insertContact(contact ?: return)
    }

    override fun insertHistory(contact: Contact?) {
        historyRepository.insertHistory(contact ?: return)
    }

}