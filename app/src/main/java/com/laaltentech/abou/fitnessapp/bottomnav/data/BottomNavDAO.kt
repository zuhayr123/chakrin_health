package com.laaltentech.abou.fitnessapp.bottomnav.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BottomNavDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfileData(profileData: ProfileData)

    @Query("SELECT * FROM ProfileData WHERE phoneNumber = :phoneNumber")
    fun loadUserByPh(phoneNumber : String?): LiveData<ProfileData>

    @Query("UPDATE ProfileData SET isSub = :isSub WHERE phoneNumber = :phoneNumber")
    fun updateTour(phoneNumber: String, isSub: Boolean?) : Int

}