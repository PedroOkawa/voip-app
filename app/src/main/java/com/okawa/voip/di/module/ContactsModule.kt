package com.okawa.voip.di.module

import com.okawa.voip.presenter.contacts.ContactsPresenter
import com.okawa.voip.presenter.contacts.ContactsPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class ContactsModule {

    @Binds
    abstract fun bindsContactsPresenter(contactsPresenterImpl: ContactsPresenterImpl): ContactsPresenter

}