package com.example.submission5setengah.core.data.source.local

import com.example.submission5setengah.core.data.source.local.entity.UserEntity
import com.example.submission5setengah.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSource @Inject constructor(private val userDao: UserDao){

    fun getListFavo(): Flow<List<UserEntity>> = userDao.getListUser()

    fun getDetailFavo(username: String): Flow<List<UserEntity>> = userDao.getUser(username)

    suspend fun insertFavo(user: UserEntity) = userDao.insertUser(user)

    suspend fun deleteFavo(username: String) = userDao.deleteUserFav(username)


}