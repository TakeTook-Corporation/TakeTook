package com.example.taketook2.ui.profile.pin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author y.gladkikh
 */
class PinEntryFragmentViewModel : ViewModel() {

    val codeLiveData: MutableLiveData<String> = MutableLiveData()
    val codeFromSms: MutableLiveData<String> = MutableLiveData()

    fun compareCodes(code: String): Boolean  = code == codeFromSms.value

    fun getCode() {
        codeFromSms.value = "123456"
    }

    init {
        getCode()
    }
}
