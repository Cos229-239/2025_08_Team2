package com.example.ravengamingnews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.ui.components.CategoryGroupPR
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme
import com.example.ravengamingnews.util.Category
import com.example.ravengamingnews.util.getContentCategories
import com.example.ravengamingnews.util.getGamesCategories
import com.example.ravengamingnews.util.getPlatformCategories


val roundedCornerSize = 8.dp

@Composable
private fun getTestCategories(): List<Category> {
    val testNumbers: IntRange = 1..4
    val categories = mutableListOf<Category>()
    categories.add( //Adding image category for demonstration
        Category(
            title = "Test",
            image = painterResource(
                id = R.drawable.patch_raven_logo_ver_3_orange)
        )
    )
    for (i in testNumbers) {
        categories.add(
            Category(
                title = "Test $i",
                image = null
            )
        )
    }
    return categories
}

@Preview
@Composable
fun CategoryButtonPreview() {
    RavenGamingNewsTheme {
        Scaffold { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryGroupPR(
                    title = "Test Category",
                    categories = getTestCategories()
                )
                CategoryGroupPR(
                    title = "Test Category 2",
                    categories = getTestCategories()
                )
            }
        }
    }
}

@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
        ) {
            item {
                CategoryGroupPR(
                    title = "Games",
                    categories = getGamesCategories()
                )
            }
            item {
                CategoryGroupPR(
                    title = "Platforms",
                    categories = getPlatformCategories()
                )
            }
            item {
                CategoryGroupPR(
                    title = "Content",
                    categories = getContentCategories()
                )
            }
        }
    }
}