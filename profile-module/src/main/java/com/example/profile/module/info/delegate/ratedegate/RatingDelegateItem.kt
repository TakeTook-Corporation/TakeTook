package com.example.profile.module.info.delegate.ratedegate

import com.example.recycler_utils.DelegateItem

data class RatingDelegateItem(
    val id: Int,
    val value: RatingModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as RatingDelegateItem).value == content()
    }
}