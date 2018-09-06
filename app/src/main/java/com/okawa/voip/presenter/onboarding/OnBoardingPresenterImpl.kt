package com.okawa.voip.presenter.onboarding

import android.content.Context
import com.okawa.voip.model.CountryCode
import com.okawa.voip.repository.PhoneNumberRepository
import com.okawa.voip.repository.account.AccountRepository
import javax.inject.Inject

class OnBoardingPresenterImpl @Inject constructor(private val accountRepository: AccountRepository, private val phoneNumberRepository: PhoneNumberRepository) : OnBoardingPresenter {

    private val countryCodes: List<CountryCode> by lazy {
        phoneNumberRepository.retrieveCountryCodes()
    }

    override fun retrieveRegion(countryName: String): String {
        val countryCode = countryCodes.firstOrNull {  countryCode ->
            countryCode.name == countryName
        }

        return countryCode?.region ?: ""
    }

    override fun retrieveCountries() = countryCodes.map { countryCode -> countryCode.name }

    override fun validatePhoneNumber(region: String, phoneNumber: String) = phoneNumberRepository.validateNumber(region, phoneNumber)

    override fun storeAccount(context: Context, token: String, listener: () -> Unit) {
        accountRepository.storeAccount(context, token, listener)
    }

}