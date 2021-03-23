package com.laaltentech.abou.fitnessapp.di.module

import com.laaltentech.abou.fitnessapp.di.WebService
import com.laaltentech.abou.fitnessapp.game.data.GameDAO
import com.laaltentech.abou.fitnessapp.game.repository.GameDataRepository
import com.laaltentech.abou.fitnessapp.login.data.LoginDAO
import com.laaltentech.abou.fitnessapp.login.repository.LoginRepository
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

    @Provides
    @Singleton
    fun provideLoginRepository(webservice: WebService, dao: LoginDAO, executor: AppExecutors): LoginRepository {
        return LoginRepository(webService = webservice, loginDAO = dao, appExecutors = executor)
    }

}