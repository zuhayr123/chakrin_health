package com.laaltentech.abou.chakra.di.module

import com.laaltentech.abou.chakra.di.WebService
import com.laaltentech.abou.chakra.game.data.GameDAO
import com.laaltentech.abou.chakra.game.repository.GameDataRepository
import com.laaltentech.abou.chakra.util.AppExecutors
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