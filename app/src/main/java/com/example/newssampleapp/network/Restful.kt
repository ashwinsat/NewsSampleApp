package com.example.newssampleapp.network

import com.example.newssampleapp.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Url

interface Restful {
    //        https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a49c5adc1a3f40048dea59d341e3c5f8
    //        https://newsapi.org/v2/everything?q=bitcoin&from=2023-06-15&sortBy=publishedAt&apiKey=API_KEY
    @GET
    suspend fun getNewsArticles(
        /*        @Query("q") searchString: String,
                @Query("from") from: String,
                @Query("sortBy") sortBy: String,
                @Query("apiKey") apiKey: String*/
        @Url url: String
    ): NewsList

}