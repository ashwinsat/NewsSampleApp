package com.example.newssampleapp.model


import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles")
    var articles: List<Article?>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)