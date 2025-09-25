package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.util.toFormattedString


@Composable
fun ArticlePage(
    articleId: String?,
    articleListViewModel: ArticleListViewModel = hiltViewModel(),
) {

    val id = articleId?.toIntOrNull()
    val article = articleListViewModel.articleList.collectAsState(emptyList()).value.find { it.id == id }

    if (article == null) {
        Text("Article not found", modifier = Modifier.padding(8.dp))
        return
    }

    val savedArticles by articleListViewModel.savedArticles.collectAsState(initial = emptySet())
    val isSaved = savedArticles.contains(article.id)

    Box(
        modifier = Modifier.fillMaxSize()){
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
                Text(
                    article.date.toFormattedString(LocalContext.current),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(article.content)

        }
        IconButton(
            onClick = { articleListViewModel.toggleSaveArticle(article.id)},
            modifier = Modifier.align(Alignment.TopEnd)
        ){
            Icon(
                imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = if (isSaved) "Saved" else "Save"
            )
        }
    }


}
