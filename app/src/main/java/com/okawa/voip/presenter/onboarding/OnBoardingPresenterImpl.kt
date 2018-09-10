package com.okawa.voip.presenter.onboarding

import android.content.Context
import com.okawa.voip.model.CountryCode
import com.okawa.voip.repository.phone.PhoneNumberRepository
import com.okawa.voip.repository.account.AccountRepository
import javax.inject.Inject

class OnBoardingPresenterImpl @Inject constructor(private val accountRepository: AccountRepository, private val phoneNumberRepository: PhoneNumberRepository) : OnBoardingPresenter {

    override fun retrieveCountries(listener: (List<CountryCode>) -> Unit) {
        phoneNumberRepository.retrieveCountryCodes {
            listener.invoke(it)
        }
    }

    override fun validatePhoneNumber(region: String, phoneNumber: String) = phoneNumberRepository.validateNumber(region, phoneNumber)

    override fun storeAccount(context: Context, token: String, listener: () -> Unit) {
        accountRepository.storeAccount(context, token, listener)
    }

}