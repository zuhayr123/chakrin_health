package com.laaltentech.abou.fitnessapp.login.viewmodels

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.laaltentech.abou.fitnessapp.login.data.SignUpData
import com.laaltentech.abou.fitnessapp.login.repository.LoginRepository
import com.laaltentech.abou.fitnessapp.network.Resource
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    var executors: AppExecutors
) : ViewModel(), Observable {

    var phoneNumber  : String = ""
    var password  : String = ""

    val apiCall = MutableLiveData<String>()
    var results: LiveData<Resource<SignUpData>>

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
                "login" -> {
                    repository.loginAttempt(phoneNumber = phoneNumber, password = password)
                }
                else -> {
                    repository.loginAttempt(phoneNumber = phoneNumber, password = password)
                }
            }
        }
    }
}