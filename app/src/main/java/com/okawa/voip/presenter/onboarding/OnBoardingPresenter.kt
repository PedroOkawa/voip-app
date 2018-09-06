package com.okawa.voip.presenter.onboarding

import android.content.Context

interface OnBoardingPresenter {

    fun retrieveRegion(countryName: String) : String

    fun retrieveCountries() : List<String>

    fun validatePhoneNumber(region: String, phoneNumber: String) : Boolean

    fun storeAccount(context: Context, token: String, listener: () -> Unit)

}