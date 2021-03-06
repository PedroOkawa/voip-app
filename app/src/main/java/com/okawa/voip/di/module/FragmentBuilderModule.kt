package com.okawa.voip.di.module

import com.okawa.voip.ui.contacts.ContactsFragment
import com.okawa.voip.ui.history.HistoryFragment
import com.okawa.voip.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ ContactsModule::class ])
    abstract fun contributesContactsFragment(): ContactsFragment

    @ContributesAndroidInjector(modules = [ HistoryModule::class ])
    abstract fun contributesHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector(modules = [ SettingsModule::class ])
    abstract fun contributesSettingsFragment(): SettingsFragment

}