package com.example.services.module

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.module.api.Api
import com.example.services.module.api.ServiceBuilder
import com.example.services.module.api.model.Listing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author y.gladkikh
 */
class ServicesViewModel : ViewModel() {
    fun getAllListingsRaw(onResult: (List<Listing>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Api::class.java)

        retrofit.getAllListings().enqueue(
            object : Callback<List<Listing>> {
                override fun onResponse(call: Call<List<Listing>>, response: Response<List<Listing>>) {
                    val listings = response.body()
                    onResult(listings)
                }

                override fun onFailure(call: Call<List<Listing>>, t: Throwable) {
                    Log.d("API", t.toString())
                    onResult(null)
                }
            }
        )
    }
}
