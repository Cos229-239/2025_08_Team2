package com.example.ravengamingnews.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.components.ArticleCard
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import kotlin.time.Clock


@Composable
fun SavedScreen(
    navigationViewModel: NavigationViewModel,
    modifier: Modifier = Modifier,
    articlesViewModel: ArticleListViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        articlesViewModel.loadSavedArticles()
    }

    val articleList = articlesViewModel.articleList.collectAsState(initial = listOf()).value
    val clickedArticles by articlesViewModel.clickedArticles.collectAsState(initial = emptyMap())
    val savedArticles by articlesViewModel.savedArticles.collectAsState(initial = emptySet())
    val savedList = articleList.filter { savedArticles.contains(it.id) }

    if(savedList.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "No Saved Articles",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(items = savedList){ item ->
                val isClicked = clickedArticles[item.id] == true
                ArticleCard(
                    articleTitle = item.title,
                    articleAuthor = item.author,
                    articlePreview = item.summary,
                    articleDate = item.date,
                    wasClicked = isClicked,
                    isSaved = true,
                    onClick = {
                        articlesViewModel.markArticleClicked(item.id)
                        navigationViewModel.navigateTo(
                            AppRoutes.ARTICLE_DETAILS.replace("{articleId}", item.id.toString())
                        )
                    },
                    onSaveClick = {articlesViewModel.toggleSaveArticle(item.id)}
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    RavenGamingNewsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ArticleCard(
                    articleTitle = "Preview Article 1",
                    articleAuthor = "Jane Doe",
                    articlePreview = "This is a preview of the saved article content.",
                    articleDate = Clock.System.now(),
                    wasClicked = false,
                    isSaved = true,
                    onClick = {},
                    onSaveClick = {}
                )
                ArticleCard(
                    articleTitle = "Preview Article 2",
                    articleAuthor = "John Smith",
                    articlePreview = "Another example of a saved article in preview.",
                    articleDate = Clock.System.now(),
                    wasClicked = true,
                    isSaved = true,
                    onClick = {},
                    onSaveClick = {}
                )
            }
        }
    }
}
