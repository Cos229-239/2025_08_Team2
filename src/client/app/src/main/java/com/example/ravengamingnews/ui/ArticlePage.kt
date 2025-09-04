package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.data.TempDataSource

@Composable
fun ArticlePage(articleId: String?) {
    val id = articleId?.toIntOrNull()
    val article = TempDataSource.fakeArticleList.find { it.id == id }

    if (article != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(Modifier.height(16.dp))
            Text(article.title, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    article.author,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))
                Text("-")
                Spacer(Modifier.width(8.dp))
                Text(article.date, style = MaterialTheme.typography.labelMedium)
            }
            Spacer(Modifier.height(16.dp))
            Text(article.content)

        }
    } else {
        Text("Article not found", modifier = Modifier.padding(8.dp))
    }
}
