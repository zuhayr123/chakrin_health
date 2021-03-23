package com.laaltentech.abou.fitnessapp.login.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "SignUpData")
class SignUpData {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("_id")
    var user_id: String? = null

    @SerializedName("first_name")
    var firstname: String? = null

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("password")
    var password: String? = null

    var confirm_password: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null

    @SerializedName("user_photo")
    var userPhoto: String? = null
}