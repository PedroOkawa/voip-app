package com.okawa.voip.di.module

import com.okawa.voip.presenter.onboarding.OnBoardingPresenter
import com.okawa.voip.presenter.onboarding.OnBoardingPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class OnBoardingModule {

    @Binds
    abstract fun bindsOnBoardingPresenter(onBoardingPresenterImpl: OnBoardingPresenterImpl) : OnBoardingPresenter

}