package com.laaltentech.abou.fitnessapp.login.viewmodels

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.login.data.SignUpData
import com.laaltentech.abou.fitnessapp.login.repository.LoginRepository
import com.laaltentech.abou.fitnessapp.network.Resource
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class SignUpViewModel@Inject constructor(
    private val repository: LoginRepository,
    var executors: AppExecutors
) : ViewModel(), Observable {

    val apiCall = MutableLiveData<String>()
    var results: LiveData<Resource<SignUpData>>

    var signUpData  = SignUpData()

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    init{
        results = Transformations.switchMap(apiCall){
            when(apiCall.value){
                "uploadUser" ->{
                    Log.e("Data", "the data to be uploaded is ${Gson().toJson(signUpData)}")
                    repository.uploadUserDetails(signUpData, true)
                }

                "uploadPhoto" -> {
                    repository.uploadProfileImage(part = prepareFilePart(signUpData.userPhoto, name = "userPdf"))
                }

                else -> {
                    repository.uploadUserDetails(signUpData, false)
                }

            }
        }
    }

    private fun prepareFilePart(url: String?, name: String): MultipartBody.Part {
        val file = File(url)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, requestBody)
    }
}