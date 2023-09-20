package com.example.submission5setengah.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission5setengah.core.domain.model.UserKu
import com.example.submission5setengah.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repo: UserUseCase): ViewModel() {


    fun getDetail(username : String) = repo.getDetailUser(username).asLiveData()

    fun findFollow(username : String, parameter : Int) = repo.getListFollow(username, parameter).asLiveData()

    fun getFavo(username:String) = repo.getStatFav(username).asLiveData()

    fun addFavo(userKu: UserKu, param: Int) {

        if (param == 0) {
            repo.insertFavo(userKu)
        } else {
            repo.deleteFavo(userKu.username)
        }

    }
}