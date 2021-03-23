package com.laaltentech.abou.fitnessapp.di

import androidx.lifecycle.LiveData
import com.laaltentech.abou.fitnessapp.game.data.GameData
import com.laaltentech.abou.fitnessapp.game.data.GameDataResponse
import com.laaltentech.abou.fitnessapp.login.data.PhotoUploadResponse
import com.laaltentech.abou.fitnessapp.login.data.SignUpData
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.util.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface WebService {
    //todo just an example to use in future cases

    @POST
    fun insertGameData(@Url url: String,
                          @Body gameData : GameData) : LiveData<ApiResponse<GameDataResponse>>

    @POST
    fun insertUserData(@Url url:String,
                        @Body data: SignUpData) : LiveData<ApiResponse<SignUpResponse>>

    @POST
    @Multipart
    fun uploadProfileImage(
        @Url url: String,
        @Part images: MultipartBody.Part
    ): LiveData<ApiResponse<PhotoUploadResponse>>
}