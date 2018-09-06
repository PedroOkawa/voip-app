package com.okawa.voip

import com.okawa.voip.repository.phone.PhoneNumberRepository
import com.okawa.voip.repository.phone.PhoneNumberRepositoryImpl
import org.junit.Test

import org.junit.Assert.*

class PhoneNumberRepositoryTest {

    companion object {
        const val BRAZIL_REGION = "BR"
        const val BRAZIL_CODE = 55
        const val BRAZIL_NUMBER = "1123312554"

        const val IRELAND_REGION = "IE"
        const val IRELAND_CODE = 353
        const val IRELAND_NUMBER = "899820726"

        const val PORTUGAL_REGION = "PT"
        const val PORTUGAL_CODE = 351
        const val PORTUGAL_NUMBER = "932662032"
    }

    private val phoneNumberRepository: PhoneNumberRepository = PhoneNumberRepositoryImpl()

    /**
     * Tests if the retrieved country codes are ok validating against three countries (Brazil, Ireland and Portugal)
     */
    @Test
    fun testCountryCodes() {
        val countryCodes = phoneNumberRepository.retrieveCountryCodes()

        assertNotNull(countryCodes)
        assertTrue(countryCodes.isNotEmpty())

        val brazil = countryCodes.first { countryCode ->
            countryCode.region == BRAZIL_REGION
        }

        assertNotNull(brazil)
        assertEquals(brazil.code, BRAZIL_CODE)

        val ireland = countryCodes.first { countryCode ->
            countryCode.region == IRELAND_REGION
        }

        assertNotNull(ireland)
        assertEquals(ireland.code, IRELAND_CODE)

        val portugal = countryCodes.first { countryCode ->
            countryCode.region == PORTUGAL_REGION
        }

        assertNotNull(portugal)
        assertEquals(portugal.code, PORTUGAL_CODE)
    }

    /**
     * Tests if the phone validation is working fine against three phone numbers (Brazilian, Irish and Portuguese)
     */
    @Test
    fun testPhoneValidation() {
        assertTrue(phoneNumberRepository.validateNumber(BRAZIL_NUMBER, BRAZIL_REGION))
        assertTrue(phoneNumberRepository.validateNumber(IRELAND_NUMBER, IRELAND_REGION))
        assertTrue(phoneNumberRepository.validateNumber(PORTUGAL_NUMBER, PORTUGAL_REGION))
    }
}
