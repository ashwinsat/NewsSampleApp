@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE_FAILED
import com.example.newssampleapp.ui.ArticleListScreenViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newssampleapp.ui.theme.NewsSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val viewModel: ArticleListScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // LaunchedEffect(key1 = , block = )
        super.onCreate(savedInstanceState)
        setContent {
            NewsSampleAppTheme {
                Scaffold(
                    topBar = {
                        AppToolbar("News app")
                    },
                    content = { padding ->
                        ContentBody(padding)
                    }
                )
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

    @Composable
    private fun ContentBody(padding: PaddingValues) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        ) {
            (0..10).forEach {
                Card (
                    elevation = CardDefaults.cardElevation(4.dp),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    content = {
                        Column {
                            Row {
                                Image(imageVector = Icons.Filled.Menu, contentDescription = "")
                                Column(modifier = Modifier.padding(15.dp)) {
                                    Text(
                                        text = "Text + $it"

                                    )
                                    Text(
                                        text = "Text + $it"

                                    )
                                }
                            }

                        }
                    }
                )
            }
        }
    }

    @Composable
    fun AppToolbar(title: String) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Blue
            )
        )
    }
}