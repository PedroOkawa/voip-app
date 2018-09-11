package com.okawa.voip.presenter.details

import android.net.Uri
import com.okawa.voip.model.Contact

interface ContactDetailsPresenter {

    fun defineContact(contact: Contact?)

    fun definePhoto(photo: Uri?)

    fun getContact(): Contact?

    fun isEditMode(): Boolean

    fun generateRandomVoIPAppNumber(): String

    fun insertContact(name: String, number: String)

    fun updateContact(name: String)

    fun deleteContact()

}