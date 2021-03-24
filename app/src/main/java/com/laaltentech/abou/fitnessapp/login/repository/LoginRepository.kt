package com.laaltentech.abou.fitnessapp.login.repository

import androidx.lifecycle.LiveData
import com.laaltentech.abou.fitnessapp.di.WebService
import com.laaltentech.abou.fitnessapp.login.data.LoginDAO
import com.laaltentech.abou.fitnessapp.login.data.PhotoUploadResponse
import com.laaltentech.abou.fitnessapp.login.data.SignUpData
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.network.NetworkBoundResource
import com.laaltentech.abou.fitnessapp.network.Resource
import com.laaltentech.abou.fitnessapp.util.ApiResponse
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.URL_HUB
import okhttp3.MultipartBody
import javax.inject.Inject
import kotlin.math.sign

class LoginRepository@Inject constructor(
    private val webService: WebService,
    private val appExecutors: AppExecutors,
    private val loginDAO: LoginDAO
){
    fun uploadUserDetails ( signUpData: SignUpData, uploadStatus : Boolean) : LiveData<Resource<SignUpData>>{
        return object : NetworkBoundResource<SignUpData, SignUpResponse>(appExecutors){
            override fun saveCallResult(item: SignUpResponse) {
                signUpData.user_id = "01"
                loginDAO.insertUserData(signUpData)
            }

            override fun shouldFetch(data: SignUpData?): Boolean = uploadStatus

            override fun loadFromDb(): LiveData<SignUpData>  = loginDAO.loadUserByPh(signUpData.phoneNumber)

            override fun createCall(): LiveData<ApiResponse<SignUpResponse>> {
                return webService.insertUserData(url = URL_HUB.POST_USER_DETAILS, data = signUpData)
            }

            override fun uploadTag(): String?  = null

        }.asLiveData()
    }

    fun uploadProfileImage(part : MultipartBody.Part) : LiveData<Resource<SignUpData>>{
        return object : NetworkBoundResource<SignUpData, PhotoUploadResponse>(appExecutors){
            override fun saveCallResult(item: PhotoUploadResponse) {
//                if(item.status == "success"){
//                    val signUpData = SignUpData()
//                    signUpData.user_id = "01"
//                    signUpData.userPhoto = item.photoRes?.url
//                    loginDAO.insertUserData(signUpData)
//                }
            }

            override fun shouldFetch(data: SignUpData?): Boolean  = true

            override fun loadFromDb(): LiveData<SignUpData>  = loginDAO.loadAll()

            override fun createCall(): LiveData<ApiResponse<PhotoUploadResponse>> {
                return webService.uploadProfileImage(url = URL_HUB.PIC_UPLOAD_URL, images = part )
            }

            override fun uploadTag(): String?  = null

        }.asLiveData()
    }
}