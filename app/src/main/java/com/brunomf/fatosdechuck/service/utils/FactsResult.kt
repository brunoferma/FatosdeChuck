package com.brunomf.fatosdechuck.service.utils


sealed class FactsResult<out T> {
    data class Success<out T>(val successData: T) : FactsResult<T>()
    data class ApiError(val statusCode: Int, val error: ErrorResponse? = null) : FactsResult<Nothing>()
    object ConnectionError : FactsResult<Nothing>()
    object ServerError : FactsResult<Nothing>()
}

