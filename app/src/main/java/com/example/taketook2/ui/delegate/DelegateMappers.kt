package com.example.taketook2.ui.delegate

import com.example.emoji.fragments.delegateItem.DelegateItem
import com.example.taketook2.ui.home.recycler.stories.StoryDelegateItem
import com.example.taketook2.ui.home.recycler.stories.StoryModel
import com.example.taketook2.ui.profile.info.delegate.characteristic.CommonCharacteristicsDelegateItem
import com.example.taketook2.ui.profile.info.delegate.characteristic.CommonCharacteristicsModel
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingDelegateItem
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingModel

/*
All functions to map lists of models to abstract DelegateItem. Should we separate them later?
 */
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

fun List<RatingModel>.toRatingDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        .forEach { model ->
            delegateItemList.add(
                RatingDelegateItem(
                    id = model.id,
                    value = model
                )
            )
        }
    return delegateItemList
}

fun List<CommonCharacteristicsModel>.toCharacteristicsDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        .forEach { model ->
            delegateItemList.add(
                CommonCharacteristicsDelegateItem(
                    id = model.id,
                    value = model
                )
            )
        }
    return delegateItemList
}