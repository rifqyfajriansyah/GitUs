package com.example.submission5setengah.core.data.source.remote.network

import com.example.submission5setengah.core.data.source.remote.response.DetailResponse
import com.example.submission5setengah.core.data.source.remote.response.FollowResponse
import com.example.submission5setengah.core.data.source.remote.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
     suspend fun getListData(
        @Query("q")username: String
    ): ListResponse

    @GET("users/{id}")
     suspend fun getDetailData(
        @Path("id")id: String
    ): DetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username")id: String
    ): List<FollowResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username")id: String
    ): List<FollowResponse>

}