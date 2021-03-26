package com.laaltentech.abou.fitnessapp.bottomnav.data

import com.google.gson.annotations.SerializedName

class ProfileResponse{

    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("user")
    var user: ProfileData? = null

    @SerializedName("status")
    var status: String? = null
}