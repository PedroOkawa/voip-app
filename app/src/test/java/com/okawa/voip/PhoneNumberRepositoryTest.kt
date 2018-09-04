package com.okawa.voip

import com.okawa.voip.repository.PhoneNumberRepository
import com.okawa.voip.repository.PhoneNumberRepositoryImpl
import org.junit.Test

import org.junit.Assert.*

class PhoneNumberRepositoryTest {

    companion object {
        const val BRAZIL_REGION = "BR"
        const val BRAZIL_CODE = 55

        const val IRELAND_REGION = "IE"
        const val IRELAND_CODE = 353

        const val PORTUGAL_REGION = "PT"
        const val PORTUGAL_CODE = 351
    }

    private val phoneNumberRepository: PhoneNumberRepository = PhoneNumberRepositoryImpl()

    @Test
    fun validateCountryCodes() {
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
}
