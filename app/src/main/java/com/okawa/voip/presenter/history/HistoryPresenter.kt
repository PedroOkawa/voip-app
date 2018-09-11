package com.okawa.voip.presenter.history

import com.okawa.voip.model.History

interface HistoryPresenter {

    fun insertHistory(history: History?)

}