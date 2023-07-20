package com.example.newssampleapp.repository

import androidx.annotation.WorkerThread
import com.example.newssampleapp.model.NewsList
import com.example.newssampleapp.network.Constants.Companion.API_KEY
import com.example.newssampleapp.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepository @Inject constructor() : BaseRepository() {
    @WorkerThread
    suspend fun getDefaultNewsArticles(apiKey: String = API_KEY): NewsList? {
        return withContext(Dispatchers.IO) {
            NetworkManager.getInstance().getNetworkClient()?.getNewsArticles(
//                searchString = DEFAULT_SEARCH_STRING,
//                from = DEFAULT_FROM,
//                sortBy = DEFAULT_SORT_BY,
//                apiKey
"https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a49c5adc1a3f40048dea59d341e3c5f8"
             )
        }
    }
}