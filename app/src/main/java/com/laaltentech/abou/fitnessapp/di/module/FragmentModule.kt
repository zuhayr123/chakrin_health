package com.laaltentech.abou.fitnessapp.di.module

import com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments.HomeFragment
import com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments.MeditateFragment
import com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments.ProfileFragment
import com.laaltentech.abou.fitnessapp.game.owner.fragments.GameInstructionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    //todo just an example to add fragments here
    @ContributesAndroidInjector
    abstract fun contributeFlickrMainFragment(): GameInstructionsFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMeditateFragment(): MeditateFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}