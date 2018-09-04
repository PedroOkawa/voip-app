package com.okawa.voip.repository

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.okawa.voip.model.CountryCode
import java.util.Locale
import javax.inject.Inject

class PhoneNumberRepositoryImpl @Inject constructor() : PhoneNumberRepository {

    companion object {
        const val NO_REGION = ""
        const val UNKNOWN_REGION = "ZZ"
    }

    private val phoneNumberInstance = PhoneNumberUtil.getInstance()

    override fun retrieveCountryCodes(): List<CountryCode> {
        val supportedCallingCodes = phoneNumberInstance.supportedRegions

        return supportedCallingCodes.map { region ->
            convertToCountryCode(region)
        }
    }

    private fun convertToCountryCode(region: String?) : CountryCode {
        val locale = Locale.getDefault()
        val code = phoneNumberInstance.getCountryCodeForRegion(region)
        val name = if(region == null || region == UNKNOWN_REGION || region == PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY) NO_REGION else Locale(locale.language, region).displayCountry
        return CountryCode(code, region!!, name)
    }

}