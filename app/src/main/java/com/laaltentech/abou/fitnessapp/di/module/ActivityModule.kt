package com.laaltentech.abou.fitnessapp.di.module

import com.laaltentech.abou.fitnessapp.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.fitnessapp.cameraX.activity.CameraActivity
import com.laaltentech.abou.fitnessapp.game.owner.activity.GameActivity
import com.laaltentech.abou.fitnessapp.login.owner.activity.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeBottomMainNavActivity(): BottomMainNavActivity

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeLoginActivity(): LoginActivity

    //todo add here activies that needs to be added, an example is added below
    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeFlickrActivity(): GameActivity

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeCameraActivity(): CameraActivity

}