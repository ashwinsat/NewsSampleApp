package com.example.newssampleapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newssampleapp.model.NewsList
import com.example.newssampleapp.model.ResponseDto
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE_FAILED
import com.example.newssampleapp.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListScreenViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    @Inject
    lateinit var repository: ArticlesRepository

    private val responseLiveData = MutableLiveData<ResponseDto>()

    // TODO : Inject the repository
    fun getArticles() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = kotlin.runCatching {
                repository.getDefaultNewsArticles()
            }
            if (result.isSuccess) {
                val response = getArticlesResponseDto(result.getOrNull())
                responseLiveData.postValue(response)
            } else {
                val response = getArticlesResponseFailedDto(result.exceptionOrNull())
                responseLiveData.postValue(response)
                Log.d("ArticleListScreenViewModel", "Failed to fetch articles")
            }
        }
    }

    private fun getArticlesResponseDto(article: NewsList?): ResponseDto {
        val responseDto = ResponseDto()
        responseDto.identifier = ARTICLES_RESPONSE
        responseDto.payload = article
        return responseDto
    }

    private fun getArticlesResponseFailedDto(exception: Throwable?): ResponseDto {
        val responseDto = ResponseDto()
        responseDto.identifier = ARTICLES_RESPONSE_FAILED
        responseDto.payload = exception
        return responseDto
    }

    fun getResponseLiveData(): MutableLiveData<ResponseDto> {
        return responseLiveData
    }
}