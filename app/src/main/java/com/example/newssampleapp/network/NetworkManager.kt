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

    fun getNetworkClient(): Restful? {
        if (networkClient == null) {
            networkClient = Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(Restful::class.java)
        }
        return networkClient
    }
}