package com.example.profile.module.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.widgets.temp.Resource

/**
 * @author y.gladkikh
 */
class SignInFragmentViewModel : ViewModel() {

    var phoneFull: Boolean = false
    val response: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    fun signIn(phoneNumber: String) {
        response.value = Resource.loading(true)
        // some actions
        // getting response
        response.value = Resource.success(true)
    }
}
