package com.apps10x.weatherx.utils

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>()
    data class Error(val error: String?) : ApiResult<Nothing>()
}