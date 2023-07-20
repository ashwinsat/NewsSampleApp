@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newssampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE
import com.example.newssampleapp.network.Constants.Companion.ARTICLES_RESPONSE_FAILED
import com.example.newssampleapp.ui.ArticleListScreenViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.newssampleapp.model.Article
import com.example.newssampleapp.model.NewsList
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
                val navController = rememberNavController()

                var canPop = remember { mutableStateOf(false) }
                Scaffold(
                    topBar = {
                        AppToolbar("News app", canPop, navController)
                    },
                    content = { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding)
                        ) {

                            hostPage(navController, canPop)
                        }
                    }
                )

            }
        }
        listenToViewModel()
        viewModel.getArticles()
    }

    @Composable
    private fun hostPage(navController: NavHostController, canPop: MutableState<Boolean>) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen { index ->
                    canPop.value = true
                    navController.navigate("detail/$index")
                }
            }
            composable(
                route = "detail/{index}",
                arguments = listOf(navArgument("index") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                })
            ) { navBackStackEntry ->
                detailScreen(navBackStackEntry) {
                    canPop.value = false
                    navController.popBackStack()
                }
            }
        }
    }

    @Composable
    private fun HomeScreen(onClick: (index: Int) -> Unit) {
        val state = viewModel.getResponseLiveData().observeAsState()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val newsList = state.value?.payload as? NewsList
            LazyColumn {
                (newsList?.articles)?.forEachIndexed { index, article ->
                    item {
                        newsCard(article, index, onClick)
                    }
                }
            }
        }
    }

    @Composable
    private fun detailScreen(
        navBackStackEntry: NavBackStackEntry,
        onClick: () -> Unit
    ) {
        val state = viewModel.getResponseLiveData().observeAsState()
        val index = navBackStackEntry.arguments?.getInt("index", 0) ?: 0
        val article =
            (state.value?.payload as NewsList).articles?.get(index)
        newsCard(article, index) {
            onClick()
        }
    }

    @Composable
    fun newsCard(article: Article?, index: Int, onClick: (index: Int) -> Unit) {
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick(index) },
            content = {
                Column(modifier = Modifier.padding(8.dp)) {
//                    Image(imageVector = Icons.Filled.Menu, contentDescription = "")
                    Text(
                        text = "${article?.title}",
                        fontSize = TextUnit(16.0F, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.onTertiary // MaterialTheme.colorScheme.onSecondary
                    )


                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        AsyncImage(
                            model = article?.urlToImage,
                            contentDescription = "",
                            modifier = Modifier.size(128.dp)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "${article?.description}",
                            fontSize = TextUnit(14.0F, TextUnitType.Sp),
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }

    @Composable
    fun AppToolbar(title: String, canPop: MutableState<Boolean>, navController: NavHostController) {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = {
                    canPop.value = false
                    navController.popBackStack()
                }) {
                    if (canPop.value)
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    else {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                    }
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
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