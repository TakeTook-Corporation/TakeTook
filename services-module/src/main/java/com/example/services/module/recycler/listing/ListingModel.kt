package com.example.services.module.recycler.listing

/**
 * @author y.gladkikh
 */
data class ListingModel(
    val id: Int,
    val title: String,
    val price: Int,
    val iconLink: String,
    val city: String,
    val usingAutomatSystem: Boolean,
    )
