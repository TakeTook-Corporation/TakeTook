package com.example.home.module.recycler.nav

import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
data class NavDelegateItem(
    val id: Int,
    val value: NavModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as NavDelegateItem).value == content()
    }
}