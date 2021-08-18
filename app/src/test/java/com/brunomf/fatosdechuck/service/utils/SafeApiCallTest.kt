package com.brunomf.fatosdechuck.service.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SafeApiCallTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit the result as success when lambda returns successfully`() {
        runBlockingTest {
            val lambdaResult = true
            val result = safeApiCall(dispatcher) { lambdaResult }
            assertEquals(FactsResult.Success(lambdaResult), result)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit the result as connectionError when lambda returns throws IOException`() {
        runBlockingTest {
            val result = safeApiCall(dispatcher) { throw IOException() }
            assertEquals(FactsResult.ConnectionError, result)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should emit the result as ApiError when lambda throws HttpException`() {
        val errorBody = "{\"errors\": [\"Unexpected parameter\"]}"
            .toResponseBody("application/json"
                .toMediaTypeOrNull())

        runBlockingTest {
            val result = safeApiCall(dispatcher) {
                throw HttpException(Response.error<Any>(422, errorBody))
            }
            assertEquals(FactsResult.ApiError(422, ErrorResponse(0,null)), result)
        }
    }
}
