package com.okawa.voip

import android.app.Activity
import android.app.Application
import com.okawa.voip.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
    }

    /**
     * Injects the app component
     */
    private fun setupDependencyInjection() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

}