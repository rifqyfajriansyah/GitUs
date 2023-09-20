package com.example.submission5setengah.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission5setengah.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM userku")
    fun getListUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM userku where username = :username")
    fun getUser(username:String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(users: UserEntity)

    @Query("DELETE FROM userku WHERE username = :username")
    fun deleteUserFav(username: String)

}