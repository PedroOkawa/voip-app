package com.okawa.voip.presenter.details

import android.net.Uri
import com.okawa.voip.model.CountryCode
import com.okawa.voip.repository.phone.PhoneNumberRepository
import com.okawa.voip.repository.contacts.ContactsRepository
import javax.inject.Inject

class ContactDetailsPresenterImpl @Inject constructor(private val contactsRepository: ContactsRepository, private val phoneNumberRepository: PhoneNumberRepository) : ContactDetailsPresenter {

    override fun retrieveCountries(listener: (List<CountryCode>) -> Unit) {
        phoneNumberRepository.retrieveCountryCodes {
            listener.invoke(it)
        }
    }

    override fun validatePhoneNumber(region: String, phoneNumber: String) = phoneNumberRepository.validateNumber(region, phoneNumber)

    override fun insertContact(name: String, number: String, photo: Uri?) {
        contactsRepository.insertContact(name, number, photo)
    }

}