package com.example.services.module.api

import com.example.services.module.api.model.Listing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @Headers("Content-Type: application/json")
    @GET("/listing/all")
    fun getAllListings(): Call<List<Listing>>
}