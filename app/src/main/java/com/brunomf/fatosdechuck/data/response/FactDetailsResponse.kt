package com.brunomf.fatosdechuck.data.response

import com.google.gson.annotations.SerializedName
import com.brunomf.fatosdechuck.service.model.Fact

data class FactDetailsResponse(

    @SerializedName("categories")
    val categories: ArrayList<String>,

    @SerializedName("created_at")
    val created_at: String,

    @SerializedName("icon_url")
    val icon_url: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("updated_at")
    val updated_at: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("value")
    val value: String,
) {
    fun getFactModel() = Fact(
        categories = this.categories,
        created_at = this.created_at,
        icon_url = this.icon_url,
        id = this.id,
        updated_at = this.updated_at,
        url = this.url,
        value = this.value
    )
}
