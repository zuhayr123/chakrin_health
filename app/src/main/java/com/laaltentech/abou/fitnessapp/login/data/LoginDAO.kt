package com.laaltentech.abou.fitnessapp.login.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LoginDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(signUpData: SignUpData)

    @Query("SELECT * FROM SignUpData WHERE phoneNumber = :phoneNumber")
    fun loadUserByPh(phoneNumber : String?): LiveData<SignUpData>

    @Update
    fun updateUserData(signUpData: SignUpData)
}