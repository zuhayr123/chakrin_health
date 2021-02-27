package com.laaltentech.abou.chakra.game.repository

import androidx.lifecycle.LiveData
import com.laaltentech.abou.chakra.di.WebService
import com.laaltentech.abou.chakra.game.data.GameDAO
import com.laaltentech.abou.chakra.game.data.GameData
import com.laaltentech.abou.chakra.game.data.GameDataResponse
import com.laaltentech.abou.chakra.game.data.GameDataWithIndividualRelation
import com.laaltentech.abou.chakra.network.NetworkBoundResource
import com.laaltentech.abou.chakra.network.Resource
import com.laaltentech.abou.chakra.util.ApiResponse
import com.laaltentech.abou.chakra.util.AppExecutors
import com.laaltentech.abou.chakra.util.URL_HUB
import javax.inject.Inject

class GameDataRepository@Inject constructor(
    private val webService: WebService,
    private val appExecutors: AppExecutors,
    private val gameDAO: GameDAO){

    fun insertGameData(data : GameData, gameId : String) : LiveData<Resource<GameDataWithIndividualRelation>>{
        return object : NetworkBoundResource<GameDataWithIndividualRelation, GameDataResponse>(appExecutors){
            override fun saveCallResult(item: GameDataResponse) {
                if(item.status == "success"){
                    gameDAO.insertGameData(item.gameData!!)
                }
            }

            override fun shouldFetch(data: GameDataWithIndividualRelation?): Boolean = true

            override fun loadFromDb(): LiveData<GameDataWithIndividualRelation> {
               return gameDAO.loadAll(gameId = gameId)
            }

            override fun createCall(): LiveData<ApiResponse<GameDataResponse>> {
                return webService.insertGameData(gameData = data, url = URL_HUB.INSERT_GAME_DATA)
            }

            override fun uploadTag(): String? = null

        }.asLiveData()
    }
}