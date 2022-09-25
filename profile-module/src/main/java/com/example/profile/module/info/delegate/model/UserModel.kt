package com.example.profile.module.info.delegate.model

import com.example.profile.module.info.delegate.characteristic.UserCharacteristicsModel
import com.example.profile.module.info.delegate.profileheader.HeaderModel
import com.example.profile.module.info.delegate.ratedegate.RatingModel

/*
 * @author y.gladkikh
 */
data class UserModel(
    val userId: Int,
    val header: HeaderModel,
    val rating: RatingModel,
    val characteristics: List<UserCharacteristicsModel>,
)
