package com.okawa.voip.presenter

import com.okawa.voip.repository.PhoneNumberRepository
import javax.inject.Inject

class OnBoardingPresenterImpl @Inject constructor(private val phoneNumberRepository: PhoneNumberRepository) : OnBoardingPresenter {

    override fun retrieveCountryCodes() = phoneNumberRepository.retrieveCountryCodes()

    override fun validatePhoneNumber(region: String, phoneNumber: String) = phoneNumberRepository.validateNumber(region, phoneNumber)

}