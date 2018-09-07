package com.okawa.voip.presenter.contacts

import android.database.Cursor
import com.okawa.voip.repository.contacts.ContactsRepository
import com.okawa.voip.repository.contacts.ListContactsSuccess
import com.okawa.voip.repository.status.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ContactsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository) : ContactsPresenter {

    private val resultSubject = PublishSubject.create<Result>()

    override fun getResultObservable() = resultSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())

    override fun retrieveContacts(cursor: Cursor?) {
        contactsRepository.retrieveContacts(cursor) { contacts ->
            resultSubject.onNext(ListContactsSuccess(contacts))
        }
    }

}