package com.okawa.voip.repository.contacts

import com.okawa.voip.model.Contact

interface ContactsRepository {

    fun insertContact(contact: Contact)

}