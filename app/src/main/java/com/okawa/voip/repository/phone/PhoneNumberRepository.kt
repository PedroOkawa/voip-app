package com.okawa.voip.repository.phone

import com.okawa.voip.model.CountryCode

interface PhoneNumberRepository {

    /**
     * Retrieves all possible country codes
     */
    fun retrieveCountryCodes(listener: (List<CountryCode>) -> Unit)

    /**
     * Validates the requested phone number
     *
     * @param region region abbreviation
     * @param number phone number
     */
    fun validateNumber(region: String, number: String) : Boolean

}