package com.okawa.voip.di.module

import com.okawa.voip.utils.VoIPAppProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProviderBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesHistoryProvider(): VoIPAppProvider

}