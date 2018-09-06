package com.okawa.voip.di.module

import com.okawa.voip.ui.contacts.ContactsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ ContactsModule::class ])
    abstract fun contributesContactsFragment(): ContactsFragment

}