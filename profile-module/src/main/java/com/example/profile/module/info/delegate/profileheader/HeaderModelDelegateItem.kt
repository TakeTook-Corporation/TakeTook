package com.example.profile.module.info.delegate.profileheader

import com.example.recycler_utils.DelegateItem

/*
 * @author y.gladkikh
 */
data class HeaderModelDelegateItem(
    val id: Int,
    val value: HeaderModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as HeaderModelDelegateItem).value == content()
    }
}