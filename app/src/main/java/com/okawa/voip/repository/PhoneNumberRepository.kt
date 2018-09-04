package com.okawa.voip.repository

import com.okawa.voip.model.CountryCode

interface PhoneNumberRepository {

    fun retrieveCountryCodes() : List<CountryCode>

}