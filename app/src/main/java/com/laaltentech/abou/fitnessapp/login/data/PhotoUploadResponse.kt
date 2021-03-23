package com.laaltentech.abou.fitnessapp.login.data

import com.google.gson.annotations.SerializedName

data class PhotoUploadResponse (
    @SerializedName("photoRes")
    var photoRes: Photos? = null,

    @SerializedName("status")
    var status: String? = null
){
    inner class Photos{
        @SerializedName("url")
        var url: String? = null
    }
}