package com.example.submission5setengah.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import javax.inject.Inject
import com.example.submission5setengah.core.domain.usecase.UserUseCase

class FavoritoViewModel @Inject constructor(private  val repo: UserUseCase) : ViewModel(){

    fun getListFavo() = repo.getListUser("", 1).asLiveData()
}