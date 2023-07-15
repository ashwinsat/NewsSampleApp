package com.example.newssampleapp.network

import com.example.newssampleapp.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface Restful {

//  @GET("https://newsapi.org/v2/everything?q=bitcoin&from=2023-06-15&sortBy=publishedAt&apiKey=API_KEY")
    @GET("everything?q=bitcoin&from=2023-06-15&sortBy=publishedAt&apiKey=API_KEY")
    suspend fun getNewsArticles(
        @Query("q") searchString: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Article

}