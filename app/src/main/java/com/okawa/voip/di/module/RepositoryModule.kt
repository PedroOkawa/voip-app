package com.okawa.voip.di.module

import com.okawa.voip.repository.phone.PhoneNumberRepository
import com.okawa.voip.repository.phone.PhoneNumberRepositoryImpl
import com.okawa.voip.repository.account.AccountRepository
import com.okawa.voip.repository.account.AccountRepositoryImpl
import com.okawa.voip.repository.contacts.ContactsRepository
import com.okawa.voip.repository.contacts.ContactsRepositoryImpl
import com.okawa.voip.repository.history.HistoryRepository
import com.okawa.voip.repository.history.HistoryRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository

    @Binds
    abstract fun bindsContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl) : ContactsRepository

    @Binds
    abstract fun bindsHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl) : HistoryRepository

    @Binds
    abstract fun bindsPhoneNumberRepository(phoneNumberRepositoryImpl: PhoneNumberRepositoryImpl) : PhoneNumberRepository

}