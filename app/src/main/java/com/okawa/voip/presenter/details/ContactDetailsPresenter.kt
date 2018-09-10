package com.okawa.voip.presenter.details

import android.net.Uri
import com.okawa.voip.model.CountryCode

interface ContactDetailsPresenter {

    fun retrieveCountries(listener: (List<CountryCode>) -> Unit)

    fun validatePhoneNumber(region: String, phoneNumber: String) : Boolean

    fun insertContact(name: String, number: String, photo: Uri?)

}