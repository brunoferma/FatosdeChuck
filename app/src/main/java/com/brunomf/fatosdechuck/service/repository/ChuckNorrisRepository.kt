package com.brunomf.fatosdechuck.service.repository

import com.brunomf.fatosdechuck.service.model.Fact
import com.brunomf.fatosdechuck.service.utils.FactsResult

interface ChuckNorrisRepository {

    suspend fun getByFreeQuery(query: String): FactsResult<List<Fact>>

    suspend fun getByCategory(category: String): FactsResult<List<Fact>>

    suspend fun getByRandom(): FactsResult<List<Fact>>

    suspend fun getCategoriesList(): FactsResult<List<String>>
}
