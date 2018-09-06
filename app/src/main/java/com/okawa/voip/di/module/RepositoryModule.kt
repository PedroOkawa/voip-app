package com.okawa.voip.di.module

import com.okawa.voip.repository.PhoneNumberRepository
import com.okawa.voip.repository.PhoneNumberRepositoryImpl
import com.okawa.voip.repository.account.AccountRepository
import com.okawa.voip.repository.account.AccountRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository

    @Binds
    abstract fun bindsPhoneNumberRepository(phoneNumberRepositoryImpl: PhoneNumberRepositoryImpl) : PhoneNumberRepository

}