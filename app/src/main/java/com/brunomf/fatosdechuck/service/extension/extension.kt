package com.brunomf.fatosdechuck.service.extension

import com.brunomf.fatosdechuck.data.response.FactBodyResponse
import com.brunomf.fatosdechuck.data.response.FactDetailsResponse
import com.brunomf.fatosdechuck.service.model.Fact
import retrofit2.Response


fun Response<FactBodyResponse>.toFactsList(): List<Fact> {
    val factDetailsResponse: MutableList<Fact> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            for (result in factsResultResponse.result) {
                val fact = result.getFactModel()
                factDetailsResponse.add(fact)
            }
        }
    }
    return factDetailsResponse
}

fun Response<FactDetailsResponse>.toFact(): List<Fact> {
    val factDetailsResponse: MutableList<Fact> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            val fact = factsResultResponse.getFactModel()
            factDetailsResponse.add(fact)
        }
    }
    return factDetailsResponse
}

fun Response<List<String>>.toCategoriesList(): List<String> {
    val categoriesList: MutableList<String> = mutableListOf()
    if (this.isSuccessful) {
        this.body()?.let { factsResultResponse ->
            for (result in factsResultResponse) {
                categoriesList.add(result)
            }
        }
    }
    return categoriesList
}
