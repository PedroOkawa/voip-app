package com.okawa.voip.presenter

import com.okawa.voip.repository.PhoneNumberRepository
import javax.inject.Inject

class OnBoardingPresenterImpl @Inject constructor(private val phoneNumberRepository: PhoneNumberRepository) : OnBoardingPresenter {

    override fun retrieveCountryCodes() {
        val countryCodes = phoneNumberRepository.retrieveCountryCodes()

        /* POPULATE THE COUNTRY/PHONE CODE ADAPTER */
    }
}