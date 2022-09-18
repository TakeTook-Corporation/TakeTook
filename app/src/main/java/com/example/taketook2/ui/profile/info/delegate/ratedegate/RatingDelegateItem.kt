package com.example.taketook2.ui.profile.info.delegate.ratedegate

import com.example.emoji.fragments.delegateItem.DelegateItem

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