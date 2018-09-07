package com.okawa.voip.presenter.contacts

import android.database.Cursor
import com.okawa.voip.repository.status.Result
import io.reactivex.Observable

interface ContactsPresenter {

    fun getResultObservable(): Observable<Result>

    fun retrieveContacts(cursor: Cursor?)

}