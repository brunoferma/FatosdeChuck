package com.brunomf.fatosdechuck.service.repository

import com.brunomf.fatosdechuck.data.api.ChuckNorrisApi
import com.brunomf.fatosdechuck.service.extension.toCategoriesList
import com.brunomf.fatosdechuck.service.extension.toFact
import com.brunomf.fatosdechuck.service.extension.toFactsList
import com.brunomf.fatosdechuck.service.model.Fact
import com.brunomf.fatosdechuck.service.utils.FactsResult
import com.brunomf.fatosdechuck.service.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO


class ChuckNorrisRepositoryImpl(
    private val api: ChuckNorrisApi,
    private val dispatcher: CoroutineDispatcher = IO
) : ChuckNorrisRepository {

    override suspend fun getByFreeQuery(query: String): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getByFreeQuery(query).toFactsList() }
    }

    override suspend fun getByCategory(category: String): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getByCategory(category).toFact() }
    }

    override suspend fun getByRandom(): FactsResult<List<Fact>> {
        return safeApiCall(dispatcher) { api.getRandom().toFact() }
    }

    override suspend fun getCategoriesList(): FactsResult<List<String>> {
        return safeApiCall(dispatcher) { api.getCategoriesList().toCategoriesList() }
    }
}
