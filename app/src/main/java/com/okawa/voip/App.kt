package com.okawa.voip

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.Context
import com.okawa.voip.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasContentProviderInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasContentProviderInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var contentProviderInjector: DispatchingAndroidInjector<ContentProvider>

    override fun activityInjector() = androidInjector

    override fun contentProviderInjector() = contentProviderInjector

    override fun attachBaseContext(base: Context?) {
        setupDependencyInjection()
        super.attachBaseContext(base)
    }

    /**
     * Injects the app component
     */
    private fun setupDependencyInjection() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

}