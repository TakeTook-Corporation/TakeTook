package com.example.home.module.recycler.mappers

import com.example.home.module.recycler.nav.NavDelegateItem
import com.example.home.module.recycler.nav.NavModel
import com.example.home.module.recycler.stories.StoryModel
import com.example.recycler_utils.DelegateItem

/*
 * @author y.gladkikh
 */
fun List<StoryModel>.toStoryDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this.forEach { model ->
            delegateItemList.add(
                com.example.home.module.recycler.stories.StoryDelegateItem(
                    id = model.id,
                    value = model,
                )
            )
        }
    return delegateItemList
}

fun List<NavModel>.toNavDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this.forEach { model ->
        delegateItemList.add(
            NavDelegateItem(
                id = model.id,
                value = model,
            )
        )
    }
    return delegateItemList
}