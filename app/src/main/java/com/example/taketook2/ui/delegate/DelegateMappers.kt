package com.example.taketook2.ui.delegate

import com.example.emoji.fragments.delegateItem.DelegateItem
import com.example.taketook2.ui.home.recycler.StoryDelegateItem
import com.example.taketook2.ui.home.recycler.StoryModel
import java.util.*

fun List<StoryModel>.toDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        //      .sortedBy { it.date.toInt() + valueToInt.getValueByMonth(it.month.lowercase(Locale.getDefault())) }
        .forEach { model ->
            delegateItemList.add(
                StoryDelegateItem(
                    id = model.id,
                    value = model,
                )
            )
        }
    return delegateItemList
}