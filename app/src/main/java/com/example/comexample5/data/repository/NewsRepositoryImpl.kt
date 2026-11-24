
package com.example.comexample5.data.repository

import com.example.comexample5.domain.model.NewsArticle
import com.example.comexample5.domain.model.NewsCategory
import com.example.comexample5.domain.model.NewsSource
import com.example.comexample5.domain.repository.NewsRepository

class NewsRepositoryImpl : NewsRepository {

    private val newsItems = listOf(
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

    override suspend fun getNews(): List<NewsArticle> {
        return newsItems
    }
}