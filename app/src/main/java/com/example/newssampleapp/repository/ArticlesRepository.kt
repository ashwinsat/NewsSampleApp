package com.example.newssampleapp.repository

import androidx.annotation.WorkerThread
import com.example.newssampleapp.model.Article
import com.example.newssampleapp.network.Constants.Companion.API_KEY
import com.example.newssampleapp.network.Constants.Companion.DEFAULT_FROM
import com.example.newssampleapp.network.Constants.Companion.DEFAULT_SEARCH_STRING
import com.example.newssampleapp.network.Constants.Companion.DEFAULT_SORT_BY
import com.example.newssampleapp.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepository @Inject constructor() : BaseRepository() {
    @WorkerThread
    suspend fun getDefaultNewsArticles(apiKey: String = API_KEY): Article? {
        return withContext(Dispatchers.IO) {
            NetworkManager.getInstance().getNetworkClient()?.getNewsArticles(
                searchString = DEFAULT_SEARCH_STRING,
                from = DEFAULT_FROM,
                sortBy = DEFAULT_SORT_BY,
                apiKey
            )
        }
    }
}