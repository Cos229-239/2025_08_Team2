package com.example.ravengamingnews.ui

import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.data.TempDataSource
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme



@Composable
fun ArticleCard(
    articleTitle: String,
    articleAuthor: String,
    articlePreview: String,
    articleDate: String,
    wasClicked: Boolean,
    onClick: () -> Unit
){
    val articleCardColor = if(wasClicked) MaterialTheme.colorScheme.tertiary
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
    ){
        Column(
            modifier = Modifier
                .padding(16.dp),
        ){
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
                text = articleDate,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FeedScreen(){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ){
            items(TempDataSource.fakeArticleList) { item ->

                var wasClicked by remember { mutableStateOf(false)}

                ArticleCard(
                    item.title,
                    item.author,
                    item.summary,
                    item.date,
                    wasClicked,
                    onClick = {
                        wasClicked = true
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
