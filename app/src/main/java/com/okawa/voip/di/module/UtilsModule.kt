package com.okawa.voip.di.module

import com.okawa.voip.utils.CallManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun providesCallManager() = CallManager()

}