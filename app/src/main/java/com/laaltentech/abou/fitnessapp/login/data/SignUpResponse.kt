package com.laaltentech.abou.fitnessapp.login.data

import com.google.gson.annotations.SerializedName

class SignUpResponse{

    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("user")
    var user: SignUpData? = null

    @SerializedName("status")
    var status: String? = null
}