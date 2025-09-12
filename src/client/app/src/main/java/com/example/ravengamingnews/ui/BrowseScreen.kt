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
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme

val colorStops = arrayOf(
    0.0f to Color.Black,
    0.25f to Color.DarkGray,
    0.75f to Color.DarkGray,
    1.0f to Color.Black
)
val roundedCornerSize = 8.dp

data class Category(
    val title: String,
    val image: Painter? = null
)

@Composable
fun CategoryButtonPR(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    category: Category
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(roundedCornerSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(width = 160.dp, height = 80.dp)
            .clip(RoundedCornerShape(roundedCornerSize))
            .background(Brush.verticalGradient(
                colorStops = colorStops)
            )
    ) {
        if (category.image != null)  {
            Image(
                painter = category.image,
                contentDescription = category.title,
                modifier = Modifier.size(64.dp)
            )
        } else {
            Text(
                text = category.title,
                softWrap = true,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CategoryGroupPR(
    title: String,
    categories: List<Category>,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        )

        val rows = (categories.size + 1) / 2
        val itemHeight = 106 //Assumed height of each button + padding
        val gridHeight = (rows * itemHeight + (rows - 1) * 16)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight.dp),
            userScrollEnabled = false,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryButtonPR(
                    onClick = { },
                    category = category
                )
            }
        }
    }
}

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

private fun getGamesCategories() : List<Category> {
    return listOf(
        Category(
            title = "League of Legends",
            image = null
        ),
        Category(
            title = "Overwatch",
            image = null
        ),
        Category(
            title = "Counter-Strike: Global Offensive",
            image = null
        ),
        Category(
            title = "Fortnite",
            image = null
        ),
    )
}

private fun getPlatformCategories() : List<Category> {
    return listOf(
        Category(
            title = "PC",
            image = null
        ),
        Category(
            title = "Xbox",
            image = null
        ),
        Category(
            title = "Playstation",
            image = null
        ),
        Category(
            title = "Nintendo Switch",
            image = null
        ),
    )
}

private fun getContentCategories(): List<Category> {
    return listOf(
        Category(
            title = "Game Reviews",
            image = null
        ),
        Category(
            title = "Patch Notes/Updates",
            image = null
        ),
        Category(
            title = "Upcoming Releases",
            image = null
        ),
        Category(
            title = "Developer Interviews",
            image = null
        ),
        Category(
            title = "Game Guides/Tutorials",
            image = null
        ),
        Category(
            title = "Deals & Discounts",
            image = null
        )
    )
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