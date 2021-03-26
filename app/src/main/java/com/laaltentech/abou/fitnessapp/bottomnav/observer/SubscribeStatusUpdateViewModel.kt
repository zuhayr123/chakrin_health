package com.laaltentech.abou.fitnessapp.bottomnav.observer

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.laaltentech.abou.fitnessapp.bottomnav.data.ProfileData
import com.laaltentech.abou.fitnessapp.bottomnav.repository.BottomNavMainRepository
import com.laaltentech.abou.fitnessapp.network.Resource
import com.laaltentech.abou.fitnessapp.util.AbsentLiveData
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import javax.inject.Inject

class SubscribeStatusUpdateViewModel @Inject constructor(
    private val repository: BottomNavMainRepository,
    var executors: AppExecutors
) : ViewModel(), Observable {

    val apiCall = MutableLiveData<String>()

    var results: LiveData<Resource<ProfileData>>

    var phoneNumber : String = ""


    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    init{
        results = Transformations.switchMap(apiCall){
            when(apiCall.value){
                "available" -> repository.updateSubscribeStatus(phoneNumber = phoneNumber)

                else -> {
                    AbsentLiveData.create()
                }
            }
        }
    }
}