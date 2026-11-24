package com.example.comexample5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NewsCategory {
    TECHNOLOGY,
    POLITICS,
    BUSINESS,
    SCIENCE,
    SPORTS
}

data class NewsSource(
    val name: String,
    val country: String
)

data class NewsArticle(
    val id: Int,
    val title: String,
    val description: String,
    val category: NewsCategory,
    val source: NewsSource,
    val publishedAt: String,
    val savedOffline: Boolean = false
)

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

val sampleNews = listOf(
    NewsArticle(
        1,
        "Нові технології штучного інтелекту",
        "Компанії розробляють нові системи штучного інтелекту для автоматизації роботи.",
        NewsCategory.TECHNOLOGY,
        NewsSource("Tech News", "Україна"),
        "23.11.2026",
        true
    ),
    NewsArticle(
        2,
        "Розвиток світової економіки",
        "Експерти аналізують зміни світової економіки та фінансових ринків.",
        NewsCategory.BUSINESS,
        NewsSource("Business Daily", "США"),
        "22.11.2026"
    ),
    NewsArticle(
        3,
        "Нові відкриття в науці",
        "Науковці представили результати досліджень у сфері сучасної медицини.",
        NewsCategory.SCIENCE,
        NewsSource("Science World", "Велика Британія"),
        "21.11.2026",
        true
    ),
    NewsArticle(
        4,
        "Результати спортивних змагань",
        "Команди продемонстрували високий рівень підготовки під час змагань.",
        NewsCategory.SPORTS,
        NewsSource("Sport News", "Україна"),
        "20.11.2026"
    ),
    NewsArticle(
        5,
        "Політичні події у світі",
        "У різних країнах відбулися важливі політичні події.",
        NewsCategory.POLITICS,
        NewsSource("World News", "Німеччина"),
        "19.11.2026"
    ),
    NewsArticle(
        6,
        "Розвиток мобільних застосунків",
        "Розробники використовують сучасні інструменти для створення мобільних програм.",
        NewsCategory.TECHNOLOGY,
        NewsSource("Mobile Today", "Україна"),
        "18.11.2026"
    ),
    NewsArticle(
        7,
        "Дослідження космосу",
        "Вчені продовжують досліджувати планети та інші об'єкти Сонячної системи.",
        NewsCategory.SCIENCE,
        NewsSource("Space News", "США"),
        "17.11.2026"
    ),
    NewsArticle(
        8,
        "Новини футбольного сезону",
        "Футбольні клуби готуються до наступних матчів та змагань.",
        NewsCategory.SPORTS,
        NewsSource("Football News", "Іспанія"),
        "16.11.2026"
    )
)

data class ListUiState(
    val news: List<NewsArticle> = emptyList(),
    val searchText: String = "",
    val isLoading: Boolean = false,
    val selectedArticleId: Int? = null
) {
    val filteredNews: List<NewsArticle>
        get() = news.filter {
            it.title.contains(searchText, ignoreCase = true) ||
                    it.description.contains(searchText, ignoreCase = true)
        }
}

class ListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ListUiState(news = sampleNews)
    )

    val uiState = _uiState.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    fun onArticleSelected(id: Int) {
        _uiState.update {
            it.copy(selectedArticleId = id)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                NewsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    newsViewModel: ListViewModel = viewModel()
) {
    val uiState by newsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Стрічка новин")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = uiState.searchText,
                onValueChange = {
                    newsViewModel.onSearchTextChanged(it)
                },
                label = {
                    Text("Пошук новин")
                },
                placeholder = {
                    Text("Введіть назву або опис...")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Знайдено новин: ${uiState.filteredNews.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.filteredNews.isEmpty()) {
                Text("Новин не знайдено")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.filteredNews) { article ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    newsViewModel.onArticleSelected(article.id)

                                    Toast.makeText(
                                        context,
                                        "Ви обрали: ${article.title}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = article.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = article.description,
                                    maxLines = 2
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text("Категорія: ${article.category}")
                                Text("Джерело: ${article.source.name}")
                                Text("Дата: ${article.publishedAt}")

                                Text(
                                    text = if (article.savedOffline) {
                                        "Збережено офлайн"
                                    } else {
                                        "Не збережено офлайн"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}