package com.example.submission5setengah.core.domain.usecase

import com.example.submission5setengah.core.data.Resource
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun getListUser(value:String, valueP:Int =0): Flow<com.example.submission5setengah.core.data.Resource<List<UserKu>>>

    fun getDetailUser(value: String) : Flow<com.example.submission5setengah.core.data.Resource<DetailKu>>

    fun getListFollow(value:String, valueP:Int): Flow<com.example.submission5setengah.core.data.Resource<List<UserKu>>>

    fun getStatFav(value: String) : Flow<List<UserKu>>

    fun insertFavo(value: UserKu)

    fun deleteFavo(value: String)

}