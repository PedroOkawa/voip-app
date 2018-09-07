package com.okawa.voip.presenter.history

import android.database.Cursor
import com.okawa.voip.repository.status.Result
import io.reactivex.Observable

interface HistoryPresenter {

    fun getResultObservable(): Observable<Result>

    fun retrieveHistory(cursor: Cursor?)

}