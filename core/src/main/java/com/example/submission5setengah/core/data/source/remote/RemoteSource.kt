package com.example.submission5setengah.core.data.source.remote

import android.util.Log
import com.example.submission5setengah.core.data.source.remote.network.ApiResponse
import com.example.submission5setengah.core.data.source.remote.network.ApiService
import com.example.submission5setengah.core.data.source.remote.response.DetailResponse
import com.example.submission5setengah.core.data.source.remote.response.FollowResponse
import com.example.submission5setengah.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSource @Inject constructor(private val apiService: ApiService){

    suspend fun getListuser(username:String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getListData(username)
                emit(ApiResponse.Success(response.items))

            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username:String): Flow<ApiResponse<DetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetailData(username)
                emit(ApiResponse.Success(response))

            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListFollow(value:String, param: Int): Flow<ApiResponse<List<FollowResponse>>> {
        return flow {

            val response = apiService
            try {

                if(param==0){
                    emit(ApiResponse.Success(response.getFollowers(value)))
                }else{
                    emit(ApiResponse.Success(response.getFollowing(value)))
                }

            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    /*companion object {
        @Volatile
        private var instance: RemoteSource? = null
        fun getInstance(
            apiService: ApiService
        ): RemoteSource =
            instance ?: synchronized(this) {
                instance ?: RemoteSource(apiService)
            }.also { instance = it }
    }*/
}