package com.example.ravengamingnews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.components.ArticleCard

@Composable
fun FeedScreenAlt(
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    articlesViewModel: ArticleListViewModel = hiltViewModel(),
    filtersViewModel: FiltersViewModel = hiltViewModel()
) {
    val gameFilters = filtersViewModel.gameFilters.collectAsState().value
    val topicFilters = filtersViewModel.topicFilters.collectAsState().value
    val articleList =
        articlesViewModel.articleList.collectAsState(initial = listOf()).value
    val clickedArticles by articlesViewModel.clickedArticles.collectAsState(initial = emptyMap())
    val isRefreshing = articlesViewModel.isRefreshing.collectAsState(false).value
    val isLoading = articlesViewModel.isLoading.collectAsState(false).value
    val savedArticles by articlesViewModel.savedArticles.collectAsState(initial = emptySet())
    val initialLoadComplete = articlesViewModel.initialLoadComplete.collectAsState().value

    LaunchedEffect(Unit) {
        filtersViewModel.loadUserFilters()
    }

    LaunchedEffect(gameFilters, topicFilters, initialLoadComplete) {
        if (initialLoadComplete) {
            articlesViewModel.getArticlesByFilters(gameFilters, topicFilters)
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { articlesViewModel.refreshArticlesByFilters(gameFilters, topicFilters) },
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement
                .spacedBy(8.dp)
        ) {
            if (articleList.isEmpty()) {
                item {
                    Text(
                        text = "No articles available.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            items(items = articleList) { item ->
                val isClicked = clickedArticles[item.id] == true
                ArticleCard(
                    item.title,
                    item.author,
                    item.summary,
                    item.date,
                    isSaved = savedArticles.contains(item.id),
                    onSaveClick = { articlesViewModel.toggleSaveArticle(item.id) },
                    wasClicked = isClicked,
                    onClick = {
                        articlesViewModel.markArticleClicked(item.id)
                        navigationViewModel.navigateTo(
                            AppRoutes.ARTICLE_DETAILS.replace(
                                "{articleId}",
                                item.id.toString()
                            )
                        )
                    }
                )
            }
        }
    }
}
