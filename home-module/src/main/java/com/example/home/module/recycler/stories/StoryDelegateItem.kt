package com.example.home.module.recycler.stories

import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
data class StoryDelegateItem(
    val id: Int,
    val value: StoryModel,
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as StoryDelegateItem).value == content()
    }
}
