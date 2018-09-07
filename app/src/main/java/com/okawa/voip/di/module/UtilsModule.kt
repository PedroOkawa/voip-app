package com.okawa.voip.di.module

import com.okawa.voip.utils.utils.PermissionUtils
import com.okawa.voip.utils.manager.CallManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun providesCallManager() = CallManager()

    @Singleton
    @Provides
    fun providesPermissionUtils() = PermissionUtils()

}