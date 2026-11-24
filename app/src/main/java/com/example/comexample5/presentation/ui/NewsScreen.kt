
package com.example.comexample5.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

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

            // Поле пошуку
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

            // Відображення стану завантаження
            if (uiState.isLoading) {
                CircularProgressIndicator()

            } else if (uiState.errorMessage != null) {
                Text(uiState.errorMessage!!)

            } else if (uiState.filteredNews.isEmpty()) {
                Text("Новин не знайдено")

            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = uiState.filteredNews,
                        key = { article -> article.id }
                    ) { article ->

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