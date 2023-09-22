package com.example.submission5setengah.core.domain.usecase

import com.example.submission5setengah.core.data.Resource
import com.example.submission5setengah.core.domain.model.DetailKu
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.domain.repository.IntUserRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val repo: IntUserRepo): UserUseCase {

    override fun getListUser(value: String, valueP: Int): Flow<Resource<List<UserKu>>> = repo.getListUser(value, valueP)

    override fun getDetailUser(value: String): Flow<Resource<DetailKu>> = repo.getDetailUser(value)

    override fun getListFollow(value: String, valueP: Int): Flow<Resource<List<UserKu>>> = repo.getListFollow(value, valueP)

    override fun getStatFav(value: String): Flow<List<UserKu>> = repo.getStatFav(value)

    override suspend fun insertFavo(value: UserKu) = repo.insertFavo(value)

    override suspend fun deleteFavo(value: String) = repo.deleteFavo(value)


}