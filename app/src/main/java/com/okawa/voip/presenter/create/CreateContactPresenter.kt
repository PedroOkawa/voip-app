package com.okawa.voip.presenter.create

import android.net.Uri

interface CreateContactPresenter {

    fun retrieveRegion(countryName: String) : String

    fun retrieveCountries() : List<String>

    fun validatePhoneNumber(region: String, phoneNumber: String) : Boolean

    fun insertContact(name: String, number: String, photo: Uri?)

}