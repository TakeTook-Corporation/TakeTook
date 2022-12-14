package com.example.profile.module.info.delegate.characteristic

import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
data class UserCharacteristicsDelegateItem(
    val id: Int,
    val value: UserCharacteristicsModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as UserCharacteristicsDelegateItem).value == content()
    }
}