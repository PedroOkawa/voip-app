package com.okawa.voip.di.component

import android.app.Application
import com.okawa.voip.App
import com.okawa.voip.di.module.ActivityBuilderModule
import com.okawa.voip.di.module.UtilsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivityBuilderModule::class,
    AndroidSupportInjectionModule::class,
    UtilsModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)

}
