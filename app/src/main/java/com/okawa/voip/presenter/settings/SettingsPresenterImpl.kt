package com.okawa.voip.presenter.settings

import com.okawa.voip.repository.contacts.ContactsRepository
import com.okawa.voip.repository.history.HistoryRepository
import javax.inject.Inject

class SettingsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository, private val historyRepository: HistoryRepository) : SettingsPresenter {

    override fun clearHistory() {
        historyRepository.deleteHistory()
    }

    override fun clearAllData() {
        contactsRepository.deleteAllData()
    }

    override fun logout() {

    }
}