package com.okawa.voip.presenter.contacts

import com.okawa.voip.model.Contact

interface ContactsPresenter {

    fun insertContact(contact: Contact?)

    fun insertHistory(contact: Contact?)

}