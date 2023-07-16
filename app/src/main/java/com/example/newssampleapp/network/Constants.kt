package com.example.newssampleapp.network

class Constants {
    companion object {
        var BASE_URL = "https://newsapi.org/v2/"
//      var API_KEY = "b6bf5f33c6944509a1c1e00a7c262e5f"
        var API_KEY = "a49c5adc1a3f40048dea59d341e3c5f8"
        var DEFAULT_SEARCH_STRING = "bitcoin"
        var DEFAULT_FROM: String = "2023-06-15"
        var DEFAULT_SORT_BY: String = "publishedAt"

        // Response identifiers
        var ARTICLES_RESPONSE = 1001
        var ARTICLES_RESPONSE_FAILED = 2001
    }
}