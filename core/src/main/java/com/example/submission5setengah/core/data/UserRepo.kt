package com.example.submission5setengah.core.data

import android.util.Log
import com.example.submission5setengah.core.data.source.local.LocalSource
import com.example.submission5setengah.core.data.source.remote.RemoteSource
import com.example.submission5setengah.core.data.source.remote.network.ApiResponse
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.domain.repository.IntUserRepo
import com.example.submission5setengah.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val localSource : LocalSource,
    private val remoteSource : RemoteSource
) : IntUserRepo{


    override fun getListUser(value:String, valueP:Int): Flow<Resource<List<UserKu>>> = flow {
        emit(Resource.Loading)
        try {
            if(valueP==0) {
                when (val apiResponse = remoteSource.getListuser(value).first()){
                    is ApiResponse.Success -> {
                        emitAll(remoteSource.getListuser(value).map { Resource.Success(DataMapper.responseUserToDomain(apiResponse.data)) })
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(apiResponse.errorMessage))
                    }
                }
            }else{
                emitAll(localSource.getListFavo().map { Resource.Success(DataMapper.entitiyUserToDomain(it)) })
            }
        }catch (e: Exception) {
            Log.d("UserInfo", "getUser: ${e.message.toString()} ")
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getDetailUser(value: String) : Flow<Resource<DetailKu>> = flow {

        emit(Resource.Loading)
        try {
            when (val apiResponse = remoteSource.getDetailUser(value).first()){
                is ApiResponse.Success -> {
                    emitAll(remoteSource.getDetailUser(value).map { Resource.Success(DataMapper.responseDetailToDomain(apiResponse.data)) })
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }

        }catch (e: Exception) {
            Log.d("Detail user", "userdetail: ${e.message.toString()} ")
            emit(Resource.Error(e.message.toString()))
        }

    }

    override fun getListFollow(value:String, valueP:Int): Flow<Resource<List<UserKu>>> = flow {

        emit(Resource.Loading)
        try {

            when (val apiResponse = remoteSource.getListFollow(value, valueP).first()){
                is ApiResponse.Success -> {
                    emitAll(remoteSource.getListFollow(value, valueP).map { Resource.Success(DataMapper.responseFollowToDomain(apiResponse.data)) })
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }

        }catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Resource.Error(e.message.toString()))
        }

    }

    override fun getStatFav(value: String) : Flow<List<UserKu>> {
        return localSource.getDetailFavo(value).map { DataMapper.entitiyUserToDomain(it) }
    }

    override suspend fun insertFavo(value: UserKu){
        localSource.insertFavo(DataMapper.domainUsertoEntity(value))
    }

    override suspend fun deleteFavo(value: String){
        localSource.deleteFavo(value)
    }


}