package com.example.submission5setengah.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserKu(
    val username : String,
    val imageUrl : String
): Parcelable