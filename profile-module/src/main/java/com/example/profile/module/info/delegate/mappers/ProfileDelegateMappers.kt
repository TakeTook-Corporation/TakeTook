package com.example.profile.module.info.delegate.mappers

import com.example.profile.module.info.delegate.characteristic.UserCharacteristicsDelegateItem
import com.example.profile.module.info.delegate.characteristic.UserCharacteristicsModel
import com.example.profile.module.info.delegate.model.UserModel
import com.example.profile.module.info.delegate.profileheader.HeaderModelDelegateItem
import com.example.profile.module.info.delegate.ratedegate.RatingDelegateItem
import com.example.profile.module.info.delegate.ratedegate.RatingModel
import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
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