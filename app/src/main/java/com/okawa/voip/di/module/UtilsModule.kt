package com.okawa.voip.di.module

import com.okawa.voip.utils.utils.PermissionUtils
import com.okawa.voip.utils.manager.CallManager
import com.okawa.voip.utils.mapper.ContactMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun providesCallManager(contactMapper: ContactMapper) = CallManager(contactMapper)

    @Singleton
    @Provides
    fun providesPermissionUtils() = PermissionUtils()

}