package com.okawa.voip.di.module

import com.okawa.voip.presenter.contacts.ContactsPresenter
import com.okawa.voip.presenter.contacts.ContactsPresenterImpl
import com.okawa.voip.presenter.settings.SettingsPresenter
import com.okawa.voip.presenter.settings.SettingsPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class SettingsModule {

    @Binds
    abstract fun bindsSettingsPresenter(settingsPresenterImpl: SettingsPresenterImpl): SettingsPresenter

}