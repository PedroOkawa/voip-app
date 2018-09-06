package com.okawa.voip.presenter

import com.okawa.voip.model.CountryCode

interface OnBoardingPresenter {

    fun retrieveCountryCodes() : List<CountryCode>

    fun validatePhoneNumber(region: String, phoneNumber: String) : Boolean

}