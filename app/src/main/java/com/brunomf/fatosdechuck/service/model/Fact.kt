package com.brunomf.fatosdechuck.service.model

data class Fact(
    val categories: ArrayList<String>,
    val created_at: String,
    val icon_url: String,
    val id: String,
    val updated_at: String,
    val url: String,
    val value: String,
)
