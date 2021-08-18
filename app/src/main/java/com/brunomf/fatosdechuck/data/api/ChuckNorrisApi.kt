package com.brunomf.fatosdechuck.data.api

import com.brunomf.fatosdechuck.data.response.FactBodyResponse
import com.brunomf.fatosdechuck.data.response.FactDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {
    @GET("/jokes/search")
    suspend fun getByFreeQuery(
        @Query(
            value = "query",
            encoded = true
        ) query: String
    ): Response<FactBodyResponse>

    @GET("/jokes/random")
    suspend fun getByCategory(
        @Query(
            value = "category",
            encoded = true
        ) category: String
    ): Response<FactDetailsResponse>

    @GET("/jokes/random")
    suspend fun getRandom(): Response<FactDetailsResponse>

    @GET("/jokes/categories")
    suspend fun getCategoriesList(): Response<List<String>>
}
