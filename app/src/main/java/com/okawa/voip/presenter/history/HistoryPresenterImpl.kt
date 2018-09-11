package com.okawa.voip.presenter.history
import com.okawa.voip.model.History
import com.okawa.voip.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryPresenterImpl @Inject constructor(private val historyRepository: HistoryRepository) : HistoryPresenter {

    override fun insertHistory(history: History?) {
        historyRepository.insertHistory(history ?: return)
    }

}