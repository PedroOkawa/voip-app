package com.okawa.voip.presenter.contacts

import com.okawa.voip.repository.contacts.ContactsRepository
import javax.inject.Inject

class ContactsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository) : ContactsPresenter {

}