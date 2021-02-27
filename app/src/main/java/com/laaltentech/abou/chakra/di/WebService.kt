package com.laaltentech.abou.chakra.di

import androidx.lifecycle.LiveData
import com.laaltentech.abou.chakra.game.data.GameData
import com.laaltentech.abou.chakra.game.data.GameDataResponse
import com.laaltentech.abou.chakra.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface WebService {
    //todo just an example to use in future cases

    @POST
    fun insertGameData(@Url url: String,
                          @Body gameData : GameData) : LiveData<ApiResponse<GameDataResponse>>
}