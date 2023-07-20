package com.example.newssampleapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager private constructor() {

    private var networkClient: Restful? = null

    companion object {
        fun getInstance(): NetworkManager {
            return NetworkManager()
        }
    }

   // https://newsapi.org/v2/everything?q=bitcoin&from=2023-06-15&sortBy=publishedAt&apiKey=API_KEY")
   // https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a49c5adc1a3f40048dea59d341e3c5f8
    fun getNetworkClient(): Restful? {
        if (networkClient == null) {
            networkClient = Retrofit.Builder().baseUrl("https://newsapi.org/v2/everything/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(Restful::class.java)
        }
        return networkClient
    }
}