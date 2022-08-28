package com.example.taketook2.ui.profile.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taketook2.support.Resource

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
