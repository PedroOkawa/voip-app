package com.okawa.voip.di.module

import com.okawa.voip.presenter.history.HistoryPresenter
import com.okawa.voip.presenter.history.HistoryPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class HistoryModule {

    @Binds
    abstract fun bindsHistoryPresenter(historyPresenterImpl: HistoryPresenterImpl): HistoryPresenter

}