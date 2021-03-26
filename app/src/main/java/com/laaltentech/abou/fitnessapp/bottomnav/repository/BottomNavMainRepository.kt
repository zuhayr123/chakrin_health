package com.laaltentech.abou.fitnessapp.bottomnav.repository

import androidx.lifecycle.LiveData
import com.laaltentech.abou.fitnessapp.bottomnav.data.BottomNavDAO
import com.laaltentech.abou.fitnessapp.bottomnav.data.ProfileData
import com.laaltentech.abou.fitnessapp.bottomnav.data.ProfileResponse
import com.laaltentech.abou.fitnessapp.di.WebService
import com.laaltentech.abou.fitnessapp.network.NetworkBoundResource
import com.laaltentech.abou.fitnessapp.network.Resource
import com.laaltentech.abou.fitnessapp.util.ApiResponse
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.URL_HUB
import javax.inject.Inject

class BottomNavMainRepository@Inject constructor(
    private val webService: WebService,
    private val appExecutors: AppExecutors,
    private val bottomNavDAO: BottomNavDAO){

    fun fetchProfile(phoneNumber : String, shouldFetch : Boolean) : LiveData<Resource<ProfileData>>{
        return object : NetworkBoundResource<ProfileData, ProfileResponse>(appExecutors){
            override fun saveCallResult(item: ProfileResponse) {
                if(item.status == "success" && item.user != null){
                    bottomNavDAO.insertProfileData(item.user!!)
                }
            }

            override fun shouldFetch(data: ProfileData?): Boolean  = shouldFetch

            override fun loadFromDb(): LiveData<ProfileData> = bottomNavDAO.loadUserByPh(phoneNumber)

            override fun createCall(): LiveData<ApiResponse<ProfileResponse>> = webService.fetchProfileData(phoneNumber = phoneNumber, url = URL_HUB.FETCH_PROFILE)

            override fun uploadTag(): String? = null

        }.asLiveData()
    }

    fun updateSubscribeStatus(phoneNumber: String) : LiveData<Resource<ProfileData>>{
        return object : NetworkBoundResource<ProfileData, ProfileResponse>(appExecutors){
            override fun saveCallResult(item: ProfileResponse) {
                if(item.status == "success" && item.user != null){
                    bottomNavDAO.updateTour(isSub = true, phoneNumber = phoneNumber)
                }
            }

            override fun shouldFetch(data: ProfileData?): Boolean = true

            override fun loadFromDb(): LiveData<ProfileData>  = bottomNavDAO.loadUserByPh(phoneNumber)

            override fun createCall(): LiveData<ApiResponse<ProfileResponse>> =  webService.updateIsSub(phoneNumber = phoneNumber, url = URL_HUB.UPDATE_SUB)

            override fun uploadTag(): String? = null

        }.asLiveData()
    }

}