package com.okawa.voip.repository

import com.okawa.voip.model.CountryCode

interface PhoneNumberRepository {

    /**
     * Retrieves all possible country codes
     */
    fun retrieveCountryCodes() : List<CountryCode>

    /**
     * Validates the requested phone number
     *
     * @param number phone number
     * @param region region abbreviation
     */
    fun validateNumber(number: String, region: String) : Boolean

}