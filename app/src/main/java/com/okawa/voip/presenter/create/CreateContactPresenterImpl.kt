package com.okawa.voip.presenter.create

import android.net.Uri
import com.okawa.voip.model.CountryCode
import com.okawa.voip.repository.phone.PhoneNumberRepository
import com.okawa.voip.repository.contacts.ContactsRepository
import javax.inject.Inject

class CreateContactPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository, private val phoneNumberRepository: PhoneNumberRepository) : CreateContactPresenter {

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

    override fun insertContact(name: String, number: String, photo: Uri?) {
        contactsRepository.insertContact(name, number, photo)
    }

}