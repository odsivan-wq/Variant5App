package com.example.comexample5


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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

val newsList = listOf(
    NewsArticle(
        1,
        "Нові технології штучного інтелекту",
        "Компанії продовжують розробляти нові системи штучного інтелекту. Вони допомагають автоматизувати роботу та вирішувати складні завдання.",
        NewsCategory.TECHNOLOGY,
        NewsSource("Tech News", "Україна"),
        "23.11.2026"
    ),
    NewsArticle(
        2,
        "Розвиток світової економіки",
        "Експерти аналізують зміни світової економіки та прогнозують подальший розвиток фінансових ринків.",
        NewsCategory.BUSINESS,
        NewsSource("Business Daily", "США"),
        "22.11.2026"
    ),
    NewsArticle(
        3,
        "Нові відкриття в науці",
        "Науковці повідомили про результати досліджень, які можуть допомогти в розвитку сучасної медицини.",
        NewsCategory.SCIENCE,
        NewsSource("Science World", "Велика Британія"),
        "21.11.2026"
    ),
    NewsArticle(
        4,
        "Результати спортивних змагань",
        "Відбулися важливі спортивні змагання. Команди продемонстрували високий рівень підготовки.",
        NewsCategory.SPORTS,
        NewsSource("Sport News", "Україна"),
        "20.11.2026"
    ),
    NewsArticle(
        5,
        "Політичні новини світу",
        "У різних країнах відбулися важливі політичні події, які привернули увагу міжнародної спільноти.",
        NewsCategory.POLITICS,
        NewsSource("World News", "Німеччина"),
        "19.11.2026"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                NewsApp()
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            NewsListScreen(
                articles = newsList,
                onArticleClick = { id ->
                    navController.navigate("details/$id")
                }
            )
        }

        composable(
            route = "details/{itemId}",
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val itemId = backStackEntry.arguments?.getInt("itemId")
            val article = newsList.find { it.id == itemId }

            if (article != null) {
                DetailScreen(
                    article = article,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            } else {
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(16.dp)
                    ) {
                        Text("Новину не знайдено")
                        Button(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Text("Назад")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    articles: List<NewsArticle>,
    onArticleClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Стрічка новин")
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(articles) { article ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onArticleClick(article.id)
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
                        Text("Дата: ${article.publishedAt}")

                        if (article.savedOffline) {
                            Text("Збережено офлайн")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: NewsArticle,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Деталі новини")
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBackClick
                    ) {
                        Text("Назад")
                    }
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
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Text("ID новини: ${article.id}")
            Text("Категорія: ${article.category}")
            Text("Джерело: ${article.source.name}")
            Text("Країна: ${article.source.country}")
            Text("Дата публікації: ${article.publishedAt}")

            Text(
                text = if (article.savedOffline) {
                    "Збережено офлайн"
                } else {
                    "Не збережено офлайн"
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Повернутися до новин")
            }
        }
    }
}