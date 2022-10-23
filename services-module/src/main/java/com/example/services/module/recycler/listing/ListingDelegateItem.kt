package com.example.services.module.recycler.listing

import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
class ListingDelegateItem(
    val id: Int,
    val value: ListingModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as ListingDelegateItem).value == content()
    }
}
