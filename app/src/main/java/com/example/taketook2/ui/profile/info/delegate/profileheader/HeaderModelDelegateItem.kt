package com.example.taketook2.ui.profile.info.delegate.profileheader

import com.example.emoji.fragments.delegateItem.DelegateItem

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