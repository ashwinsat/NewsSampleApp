package com.example.newssampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE_FAILED
import com.example.newssampleapp.ui.ArticleListScreenViewModel
import com.example.newssampleapp.ui.theme.NewsSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ArticleListScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
                // test
            }
        }
        listenToViewModel()
        viewModel.getArticles()
    }

    private fun listenToViewModel() {
        viewModel.getResponseLiveData().observe(this) {
            Log.d("MainActivity", "Response received")
            when (it.identifier) {
                ARTICLES_RESPONSE -> {
                    Toast.makeText(this, "Fetched articles successfully", Toast.LENGTH_LONG).show()
                }
                ARTICLES_RESPONSE_FAILED -> {
                    Toast.makeText(this, "Fetched articles failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsSampleAppTheme {
        Greeting("Android")
    }
}