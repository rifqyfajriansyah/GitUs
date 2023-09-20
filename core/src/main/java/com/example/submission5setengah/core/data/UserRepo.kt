package com.example.submission5setengah.core.data

import android.util.Log
import com.example.submission5setengah.core.data.source.local.LocalSource
import com.example.submission5setengah.core.data.source.remote.RemoteSource
import com.example.submission5setengah.core.data.source.remote.network.ApiResponse
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.domain.repository.IntUserRepo
import com.example.submission5setengah.core.utils.AppExecutor
import com.example.submission5setengah.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val localSource : LocalSource,
    private val remoteSource : RemoteSource,
    private val appExecutor: AppExecutor
) : IntUserRepo{


    override fun getListUser(value:String, valueP:Int): Flow<com.example.submission5setengah.core.data.Resource<List<UserKu>>> = flow {
        emit(com.example.submission5setengah.core.data.Resource.Loading)
        try {
            if(valueP==0) {
                when (val apiResponse = remoteSource.getListuser(value).first()){
                    is ApiResponse.Success -> {
                        emitAll(remoteSource.getListuser(value).map { com.example.submission5setengah.core.data.Resource.Success(DataMapper.responseUserToDomain(apiResponse.data)) })
                    }
                    is ApiResponse.Error -> {
                        emit(com.example.submission5setengah.core.data.Resource.Error(apiResponse.errorMessage))
                    }
                }
            }else{
                emitAll(localSource.getListFavo().map { com.example.submission5setengah.core.data.Resource.Success(DataMapper.entitiyUserToDomain(it)) })
            }
        }catch (e: Exception) {
            Log.d("UserInfo", "getUser: ${e.message.toString()} ")
            emit(com.example.submission5setengah.core.data.Resource.Error(e.message.toString()))
        }
    }

    override fun getDetailUser(value: String) : Flow<com.example.submission5setengah.core.data.Resource<DetailKu>> = flow {

        emit(com.example.submission5setengah.core.data.Resource.Loading)
        try {
            when (val apiResponse = remoteSource.getDetailUser(value).first()){
                is ApiResponse.Success -> {
                    emitAll(remoteSource.getDetailUser(value).map { com.example.submission5setengah.core.data.Resource.Success(DataMapper.responseDetailToDomain(apiResponse.data)) })
                }
                is ApiResponse.Error -> {
                    emit(com.example.submission5setengah.core.data.Resource.Error(apiResponse.errorMessage))
                }
            }

        }catch (e: Exception) {
            Log.d("Detail user", "userdetail: ${e.message.toString()} ")
            emit(com.example.submission5setengah.core.data.Resource.Error(e.message.toString()))
        }

    }

    override fun getListFollow(value:String, valueP:Int): Flow<com.example.submission5setengah.core.data.Resource<List<UserKu>>> = flow {

        emit(com.example.submission5setengah.core.data.Resource.Loading)
        try {

            when (val apiResponse = remoteSource.getListFollow(value, valueP).first()){
                is ApiResponse.Success -> {
                    emitAll(remoteSource.getListFollow(value, valueP).map { com.example.submission5setengah.core.data.Resource.Success(DataMapper.responseFollowToDomain(apiResponse.data)) })
                }
                is ApiResponse.Error -> {
                    emit(com.example.submission5setengah.core.data.Resource.Error(apiResponse.errorMessage))
                }
            }

        }catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(com.example.submission5setengah.core.data.Resource.Error(e.message.toString()))
        }

    }

    override fun getStatFav(value: String) : Flow<List<UserKu>> {
        return localSource.getDetailFavo(value).map { DataMapper.entitiyUserToDomain(it) }
    }

    //suspend itu digunakan saat apa ya, apakah insert/delete perlu?
    override fun insertFavo(value: UserKu){
        appExecutor.diskIO().execute {localSource.insertFavo(DataMapper.domainUsertoEntity(value))}
    }

    override fun deleteFavo(value: String){
        appExecutor.diskIO().execute {localSource.deleteFavo(value)}
    }


    companion object {
        @Volatile
        private var instance: UserRepo? = null
        fun getInstance(
            localDataSource: LocalSource,
            remoteSource: RemoteSource,
            appExecutor: AppExecutor
        ): UserRepo =
            instance ?: synchronized(this) {
                instance ?: UserRepo(localDataSource, remoteSource, appExecutor)
            }.also { instance = it }
    }

}