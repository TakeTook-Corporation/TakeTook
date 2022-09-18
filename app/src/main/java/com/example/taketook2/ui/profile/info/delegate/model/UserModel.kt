package com.example.taketook2.ui.profile.info.delegate.model

import com.example.taketook2.ui.profile.info.delegate.characteristic.UserCharacteristicsModel
import com.example.taketook2.ui.profile.info.delegate.profileheader.HeaderModel
import com.example.taketook2.ui.profile.info.delegate.ratedegate.RatingModel

/*
 * @author y.gladkikh
 */
data class UserModel(
    val userId: Int,
    val header: HeaderModel,
    val rating: RatingModel,
    val characteristics: List<UserCharacteristicsModel>,
)
