package com.example.ravengamingnews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ravengamingnews.navigation.AppRoutes
import com.example.ravengamingnews.navigation.NavigationViewModel
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import com.example.ravengamingnews.utils.toFormattedString
import kotlin.time.Instant


@Composable
fun ArticleCard(
    articleTitle: String,
    articleAuthor: String,
    articlePreview: String,
    articleDate: Instant,
    wasClicked: Boolean,
    onClick: () -> Unit
) {
    val articleCardColor = if (wasClicked) MaterialTheme.colorScheme.tertiary
    else MaterialTheme.colorScheme.primaryContainer
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(175.dp)
            //.border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults
            .cardColors(
                containerColor = articleCardColor
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = articleTitle,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = articleAuthor,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = articlePreview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            Text(
                text = articleDate.toFormattedString(LocalContext.current),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FeedScreen(
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    articlesViewModel: ArticleListViewModel = hiltViewModel(),
) {
    val articleList = articlesViewModel.articleList.collectAsState(initial = listOf()).value
    val clickedArticles by articlesViewModel.clickedArticles.collectAsState(initial = emptyMap())

    if (articleList.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "No articles available.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        items(items = articleList) { item ->
            val isClicked = clickedArticles[item.id] == true
            ArticleCard(
                item.title,
                item.author,
                item.summary,
                item.date,
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

@Preview
@Composable
fun FeedScreenPreview() {
    RavenGamingNewsTheme {
        FeedScreen()
    }
}
