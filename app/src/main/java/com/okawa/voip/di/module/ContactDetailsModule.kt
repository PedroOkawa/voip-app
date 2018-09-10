package com.okawa.voip.di.module

import com.okawa.voip.presenter.details.ContactDetailsPresenter
import com.okawa.voip.presenter.details.ContactDetailsPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class ContactDetailsModule {

    @Binds
    abstract fun bindsContactDetailsPresenter(createContactPresenterImpl: ContactDetailsPresenterImpl): ContactDetailsPresenter

}