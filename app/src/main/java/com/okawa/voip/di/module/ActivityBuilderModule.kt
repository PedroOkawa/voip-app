package com.okawa.voip.di.module

import com.okawa.voip.ui.onboarding.OnBoardingActivity
import com.okawa.voip.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ OnBoardingModule::class ])
    abstract fun contributesOnBoardingActivity(): OnBoardingActivity

}