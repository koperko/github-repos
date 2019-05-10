package com.koperko.jll

import retrofit2.Response

sealed class NetworkResult <T> {

    data class Error<T>(val message: String, val code: Int) : NetworkResult<T>()
    data class Success<T>(val data: T?) : NetworkResult<T>()

}

fun <T> Response<T>.toResult(): NetworkResult<T> {
    return if (isSuccessful) {
        NetworkResult.Success(this.body())
    } else {
        NetworkResult.Error(message(), code())
    }
}