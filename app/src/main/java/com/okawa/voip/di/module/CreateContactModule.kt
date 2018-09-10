package com.okawa.voip.di.module

import com.okawa.voip.presenter.create.CreateContactPresenter
import com.okawa.voip.presenter.create.CreateContactPresenterImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ RepositoryModule::class ])
abstract class CreateContactModule {

    @Binds
    abstract fun bindsCreateContactPresenter(createContactPresenterImpl: CreateContactPresenterImpl): CreateContactPresenter

}