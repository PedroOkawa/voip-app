package com.okawa.voip.presenter.contacts

import com.okawa.voip.model.Contact
import com.okawa.voip.repository.history.HistoryRepository
import javax.inject.Inject

class ContactsPresenterImpl @Inject constructor(private val historyRepository: HistoryRepository) : ContactsPresenter {

    override fun insertHistory(contact: Contact?) {
        historyRepository.insertHistory(contact ?: return)
    }

}