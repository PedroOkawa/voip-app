package com.okawa.voip.presenter.onboarding

import android.content.Context
import com.okawa.voip.model.CountryCode

interface OnBoardingPresenter {

    fun retrieveCountries(listener: (List<CountryCode>) -> Unit)

    fun validatePhoneNumber(region: String, phoneNumber: String) : Boolean

    fun storeAccount(context: Context, token: String, listener: () -> Unit)

}