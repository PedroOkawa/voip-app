package com.okawa.voip.di.module

import com.okawa.voip.presenter.splash.SplashPresenter
import com.okawa.voip.presenter.splash.SplashPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class SplashModule {

    @Binds
    abstract fun bindsSplashPresenter(onBoardingPresenterImpl: SplashPresenterImpl) : SplashPresenter

}