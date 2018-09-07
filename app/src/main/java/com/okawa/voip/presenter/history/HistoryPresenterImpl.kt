package com.okawa.voip.presenter.history

import android.database.Cursor
import com.okawa.voip.repository.history.HistoryRepository
import com.okawa.voip.repository.history.ListHistorySuccess
import com.okawa.voip.repository.status.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HistoryPresenterImpl @Inject constructor(private val historyRepository: HistoryRepository) : HistoryPresenter {

    private val resultSubject = PublishSubject.create<Result>()

    override fun getResultObservable() = resultSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())

    override fun retrieveHistory(cursor: Cursor?) {
        historyRepository.retrieveHistory(cursor) { contacts ->
            resultSubject.onNext(ListHistorySuccess(contacts))
        }
    }

}