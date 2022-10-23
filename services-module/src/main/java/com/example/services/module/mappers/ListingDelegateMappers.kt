package com.example.services.module.mappers

import com.example.recycler_utils.DelegateItem
import com.example.services.module.recycler.listing.ListingDelegateItem
import com.example.services.module.recycler.listing.ListingModel

/**
 * @author y.gladkikh
 */
fun List<ListingModel>.toListingDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        .forEach { model ->
            delegateItemList.add(
                ListingDelegateItem(
                    id = model.id,
                    value = model
                )
            )
        }
    return delegateItemList
}
