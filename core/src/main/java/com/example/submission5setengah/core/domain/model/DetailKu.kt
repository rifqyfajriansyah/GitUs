package com.example.submission5setengah.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailKu(
    val name : String?,
    val username : String,
    val follower : Int,
    val following : Int,
    val imageUrl : String
): Parcelable