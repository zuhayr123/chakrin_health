package com.laaltentech.abou.fitnessapp.di

import android.app.Application
import com.laaltentech.abou.fitnessapp.di.module.ActivityModule
import com.laaltentech.abou.fitnessapp.di.module.AppModule
import com.laaltentech.abou.fitnessapp.util.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AndroidInjectionModule::class),
    (ActivityModule::class),
    (AppModule::class)
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppController)

}