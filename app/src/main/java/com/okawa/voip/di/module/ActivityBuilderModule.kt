package com.okawa.voip.di.module

import com.okawa.voip.ui.create.CreateContactActivity
import com.okawa.voip.ui.main.MainActivity
import com.okawa.voip.ui.onboarding.OnBoardingActivity
import com.okawa.voip.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [ SplashModule::class ])
    abstract fun contributesSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ OnBoardingModule::class ])
    abstract fun contributesOnBoardingActivity(): OnBoardingActivity

    @ContributesAndroidInjector(modules = [ FragmentBuilderModule::class ])
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ CreateContactModule::class ])
    abstract fun contributesCreateContactActivity(): CreateContactActivity

}