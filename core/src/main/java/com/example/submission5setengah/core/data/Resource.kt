package com.example.submission5setengah.core.data

sealed class Resource<out R> private constructor() {
    data class Success<out T>(val data: T) : com.example.submission5setengah.core.data.Resource<T>()
    data class Error(val error: String) : com.example.submission5setengah.core.data.Resource<Nothing>()
    object Loading : com.example.submission5setengah.core.data.Resource<Nothing>()
}