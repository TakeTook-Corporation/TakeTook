package com.example.home.module.recycler.mappers

import com.example.recycler_utils.DelegateItem

/*
 * @author y.gladkikh
 */
fun List<com.example.home.module.recycler.stories.StoryModel>.toDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        //      .sortedBy { it.date.toInt() + valueToInt.getValueByMonth(it.month.lowercase(Locale.getDefault())) }
        .forEach { model ->
            delegateItemList.add(
                com.example.home.module.recycler.stories.StoryDelegateItem(
                    id = model.id,
                    value = model,
                )
            )
        }
    return delegateItemList
}