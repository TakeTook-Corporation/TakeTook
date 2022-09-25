package com.example.taketook2.ui.delegate

import com.example.recycler_utils.DelegateItem
import com.example.taketook2.ui.home.recycler.stories.StoryDelegateItem
import com.example.taketook2.ui.home.recycler.stories.StoryModel
import com.example.taketook2.ui.profile.info.delegate.characteristic.UserCharacteristicsDelegateItem
import com.example.taketook2.ui.profile.info.delegate.characteristic.UserCharacteristicsModel
import com.example.taketook2.ui.profile.info.delegate.model.UserModel
import com.example.taketook2.ui.profile.info.delegate.profileheader.HeaderModelDelegateItem
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingDelegateItem
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingModel

/*
 * @author y.gladkikh
 * All functions to map lists of models to abstract DelegateItem. Should we separate them later?
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

fun UserModel.toProfileDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()
    delegateItemList.add(
        HeaderModelDelegateItem(
            id = this.userId,
            value = this.header,
        )
    )
    delegateItemList.add(
        RatingDelegateItem(
            id = this.userId,
            value = this.rating,
        )
    )
    this.characteristics.forEach { model ->
            delegateItemList.add(
                UserCharacteristicsDelegateItem(
                    id = model.id,
                    value = model
                )
            )
        }
    return delegateItemList
}

fun List<UserCharacteristicsModel>.toCharacteristicsDelegateItemList(): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this
        .forEach { model ->
            delegateItemList.add(
                UserCharacteristicsDelegateItem(
                    id = model.id,
                    value = model
                )
            )
        }
    return delegateItemList
}