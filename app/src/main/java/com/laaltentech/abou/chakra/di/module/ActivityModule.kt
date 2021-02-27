package com.laaltentech.abou.chakra.di.module

import com.laaltentech.abou.chakra.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.chakra.game.owner.activity.GameActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeBottomMainNavActivity(): BottomMainNavActivity

    //todo add here activies that needs to be added, an example is added below
    @ContributesAndroidInjector(modules = [(FragmentModule::class)])
    abstract fun contributeFlickrActivity(): GameActivity



}