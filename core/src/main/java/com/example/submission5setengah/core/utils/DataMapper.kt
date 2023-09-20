package com.example.submission5setengah.core.utils

import com.example.submission5setengah.core.data.source.local.entity.UserEntity
import com.example.submission5setengah.core.data.source.remote.response.DetailResponse
import com.example.submission5setengah.core.data.source.remote.response.FollowResponse
import com.example.submission5setengah.core.data.source.remote.response.UserResponse
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu

object DataMapper {

    fun entitiyUserToDomain(input: List<UserEntity>): List<UserKu> {
        val userList = ArrayList<UserKu>()
        input.map {
            val user = UserKu(
                username = it.username,
                imageUrl = it.urlImage
            )
            userList.add(user)
        }
        return userList
    }

    fun domainUsertoEntity(input: UserKu)= UserEntity (
        username = input.username,
        urlImage = input.imageUrl
    )

    fun responseUserToDomain(input: List<UserResponse>): List<UserKu> {
        val userList = ArrayList<UserKu>()
        input.map {
            val user = UserKu(
                username = it.login,
                imageUrl = it.avatarUrl
            )
            userList.add(user)
        }
        return userList
    }

    fun responseFollowToDomain(input: List<FollowResponse>): List<UserKu> {
        val userList = ArrayList<UserKu>()
        input.map {
            val user = UserKu(
                username = it.login,
                imageUrl = it.avatarUrl
            )
            userList.add(user)
        }
        return userList
    }

    fun responseDetailToDomain(input: DetailResponse) = DetailKu (
        name = input.name,
        username = input.login,
        imageUrl = input.avatarUrl,
        follower =  input.followers,
        following = input.following
    )

}