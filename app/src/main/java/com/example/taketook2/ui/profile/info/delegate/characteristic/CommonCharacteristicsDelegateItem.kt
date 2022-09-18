package com.example.taketook2.ui.profile.info.delegate.characteristic

import com.example.emoji.fragments.delegateItem.DelegateItem

data class CommonCharacteristicsDelegateItem(
    val id: Int,
    val value: CommonCharacteristicsModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as CommonCharacteristicsDelegateItem).value == content()
    }
}