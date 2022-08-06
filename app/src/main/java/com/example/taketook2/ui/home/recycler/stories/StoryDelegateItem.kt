package com.example.taketook2.ui.home.recycler.stories

import com.example.emoji.fragments.delegateItem.DelegateItem

/**
 * @author y.gladkikh
 */
data class StoryDelegateItem(
    val id : Int,
    val value : StoryModel
): DelegateItem {
    override fun content(): Any = value

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as StoryDelegateItem).value == content()
    }
}
