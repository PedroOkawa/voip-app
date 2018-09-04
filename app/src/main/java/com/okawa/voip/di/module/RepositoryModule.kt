package com.okawa.voip.di.module

import com.okawa.voip.repository.PhoneNumberRepository
import com.okawa.voip.repository.PhoneNumberRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsPhoneNumberRepository(phoneNumberRepositoryImpl: PhoneNumberRepositoryImpl) : PhoneNumberRepository

}