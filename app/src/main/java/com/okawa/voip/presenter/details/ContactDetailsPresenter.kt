package com.okawa.voip.presenter.details

import android.net.Uri

interface ContactDetailsPresenter {

    fun generateRandomVoIPAppNumber(): String

    fun insertContact(name: String, number: String, photo: Uri?)

    fun updateContact(id: String?, name: String, photo: Uri?)

    fun deleteContact(id: String?)

}