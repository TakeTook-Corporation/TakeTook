package com.example.services.module.api.model

import com.google.gson.annotations.SerializedName

data class Listing (
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("author") val author: String,
    @SerializedName("dot") val dot: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("category") val category: String,
)