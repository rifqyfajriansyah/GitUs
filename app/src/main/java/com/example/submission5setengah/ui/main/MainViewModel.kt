package com.example.submission5setengah.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission5setengah.core.domain.usecase.UserUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: UserUseCase) : ViewModel() {

    fun findList(username:String) = repo.getListUser(username).asLiveData()

}