package com.example.submission5setengah.core.domain.usecase

import com.example.submission5setengah.core.data.Resource
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun getListUser(value:String, valueP:Int =0): Flow<Resource<List<UserKu>>>

    fun getDetailUser(value: String) : Flow<Resource<DetailKu>>

    fun getListFollow(value:String, valueP:Int): Flow<Resource<List<UserKu>>>

    fun getStatFav(value: String) : Flow<List<UserKu>>

    suspend fun insertFavo(value: UserKu)

    suspend fun deleteFavo(value: String)

}