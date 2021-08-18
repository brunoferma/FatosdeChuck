package com.brunomf.fatosdechuck.service.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.brunomf.fatosdechuck.service.utils.ErrorResponse.Companion.EMPTY_API_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException



suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T

): FactsResult<T> {
    return withContext(dispatcher) {
        try {
            FactsResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> FactsResult.ConnectionError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    FactsResult.ApiError(code, errorResponse)
                }
                else -> {
                    FactsResult.ServerError
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    try {
        val errorJsonString = throwable.response()?.errorBody()?.string()
        return Gson().fromJson(errorJsonString, ErrorResponse::class.java)
    } catch (exception: Exception) {
        null
    }
    return EMPTY_API_ERROR
}

data class ErrorResponse(val code: Int, val message: String?) {
    companion object {
        val EMPTY_API_ERROR = ErrorResponse(-1, null)
    }
}

