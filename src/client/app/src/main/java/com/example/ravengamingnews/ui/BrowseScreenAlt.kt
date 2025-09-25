package com.example.ravengamingnews.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.data.Filter
import com.example.ravengamingnews.domain.model.Article
import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.components.ArticleCard
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

@Composable
fun BrowseScreenAlt(
    modifier: Modifier = Modifier,
    filtersViewModel: FiltersViewModel,
    articleListViewModel: ArticleListViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel
) {
    val games = filtersViewModel.gameFilters.collectAsState().value
    val topics = filtersViewModel.topicFilters.collectAsState().value
    val browseFilter = articleListViewModel.browseFilter.collectAsState(null).value

    LaunchedEffect(Unit) {
        filtersViewModel.loadUserFilters()
    }

    LaunchedEffect(browseFilter) {
        if (browseFilter != null) {
            articleListViewModel.getArticlesByFilter(browseFilter)
        }
    }

    BackHandler(enabled = browseFilter != null) {
        articleListViewModel.clearFilters()
    }

    if (browseFilter == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 0.dp
                ),
            ) {
                item {
                    FilterSwitchGroup(
                        title = "Games",
                        filters = games,
                        modifier = Modifier.padding(bottom = 16.dp),
                        articleListViewModel = articleListViewModel
                    )
                }
                item { HorizontalDivider(modifier = modifier.padding(bottom = 16.dp)) }
                item {
                    FilterSwitchGroup(
                        title = "Topics",
                        filters = topics,
                        modifier = Modifier.padding(bottom = 16.dp),
                        articleListViewModel = articleListViewModel
                    )
                }
            }
        }
    } else {
        val isRefreshing = articleListViewModel.isRefreshing.collectAsState(false).value
        val articleList = articleListViewModel.articleList.collectAsState(initial = listOf()).value
        val clickedArticles by articleListViewModel.clickedArticles.collectAsState(initial = emptyMap())
        val isLoading = articleListViewModel.isLoading.collectAsState(false).value
        val savedArticles by articleListViewModel.savedArticles.collectAsState(initial = emptySet())
        ArticleList(
            isLoading = isLoading,
            isRefreshing = isRefreshing,
            onRefresh = { articleListViewModel.refreshArticlesByFilter(browseFilter) },
            articleList = articleList,
            clickedArticles = clickedArticles,
            savedArticles = savedArticles,
            articlesViewModel = articleListViewModel,
            onClicked = { article ->
                articleListViewModel.markArticleClicked(article.id)
                navigationViewModel.navigateTo(
                    AppRoutes.ARTICLE_DETAILS.replace(
                        "{articleId}",
                        article.id.toString()
                    )
                )
            },
            modifier = modifier
        )
    }
}

@Composable
private fun FilterSwitchGroup(
    title: String,
    filters: List<Filter>,
    modifier: Modifier = Modifier,
    articleListViewModel: ArticleListViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier.padding(bottom = 4.dp)
        )
        filters.forEach { filter ->
            BrowseRow(
                onClick = {
                    articleListViewModel.getArticlesByFilter(filter)
                },
                filter = filter,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BrowseRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    filter: Filter,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(filter.titleResId),
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
        )
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(filter.titleResId),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ArticleList(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onClicked: (Article) -> Unit,
    articleList: List<Article>,
    clickedArticles: Map<Int, Boolean>,
    isRefreshing: Boolean,
    isLoading: Boolean,
    savedArticles: Set<Int>,
    articlesViewModel: ArticleListViewModel
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize()
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
                    onClick = { onClicked(item) }
                )
            }
        }
    }
}

@Preview
@Composable
fun BrowseRowPreview() {
    RavenGamingNewsTheme {
        FiltersScreenAlt()
    }
}
