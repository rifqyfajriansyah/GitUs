package com.example.submission5setengah.core.data

sealed class Resource<out R>{
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}