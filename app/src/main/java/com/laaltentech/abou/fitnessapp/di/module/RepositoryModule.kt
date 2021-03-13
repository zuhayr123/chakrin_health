package com.laaltentech.abou.fitnessapp.di.module

import com.laaltentech.abou.fitnessapp.di.WebService
import com.laaltentech.abou.fitnessapp.game.data.GameDAO
import com.laaltentech.abou.fitnessapp.game.repository.GameDataRepository
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    //todo provides repository

    @Provides
    @Singleton
    fun provideFlickrRepository(webservice: WebService, dao: GameDAO, executor: AppExecutors): GameDataRepository {
        return GameDataRepository(webService = webservice, gameDAO = dao, appExecutors = executor)
    }

}