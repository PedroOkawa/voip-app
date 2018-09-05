package com.okawa.voip.presenter

import com.okawa.voip.repository.PhoneNumberRepository
import javax.inject.Inject

class OnBoardingPresenterImpl @Inject constructor(private val phoneNumberRepository: PhoneNumberRepository) : OnBoardingPresenter {

    override fun retrieveCountries(): List<String> {
        return phoneNumberRepository.retrieveCountryCodes().sortedBy { countryCode ->
            countryCode.name
        }.map { countryCode ->
            countryCode.name
        }
    }

}