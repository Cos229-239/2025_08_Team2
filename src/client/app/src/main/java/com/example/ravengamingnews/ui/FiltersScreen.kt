package com.example.ravengamingnews.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ravengamingnews.R
import com.example.ravengamingnews.roundedCornerSize
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme




@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier
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
                CategoryGroupPR2(
                    title = "Games",
                    categories = getGamesCategories2(),
                    mode = CategoryGroupMode.Selectable
                )
            }
            item {
                CategoryGroupPR2(
                    title = "Platforms",
                    categories = getPlatformCategories2(),
                    mode = CategoryGroupMode.Selectable
                )
            }
            item {
                CategoryGroupPR2(
                    title = "Content",
                    categories = getContentCategories2(),
                    mode = CategoryGroupMode.Selectable
                )
            }
        }
    }
}

@Preview
@Composable
fun FiltersScreenPreview(){
    RavenGamingNewsTheme {
        FiltersScreen()
    }
}

sealed class CategoryGroupMode {
    object Selectable : CategoryGroupMode()
    object Navigable : CategoryGroupMode()
}
val colorStops = arrayOf(
    0.0f to Color.Black,
    0.25f to Color.DarkGray,
    0.75f to Color.DarkGray,
    1.0f to Color.Black
)

@Composable
fun CategoryButtonPR2(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    category: Category2
) {
    val isSelected = category.isSelected

    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(roundedCornerSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) Color.Transparent else Color.Black),
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
fun CategoryGroupPR2(
    title: String,
    categories: List<Category2>,
    mode: CategoryGroupMode,
    modifier: Modifier = Modifier,
    onCategoryClick: (Category2) -> Unit = {}
) {
    var selectedCategories by remember { mutableStateOf(categories) }

    val displayCategories = when (mode){
        is CategoryGroupMode.Selectable -> selectedCategories
        is CategoryGroupMode.Navigable -> categories
    }

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
            items(displayCategories) { category ->
                val isSelected = if (mode is CategoryGroupMode.Selectable) category.isSelected else false

                CategoryButtonPR2(
                    category = category,
                    onClick = {
                        when(mode){
                            is CategoryGroupMode.Selectable -> {
                                selectedCategories = selectedCategories.map{
                                    if(it.title == category.title) it.copy(isSelected = !it.isSelected)
                                    else it
                                }
                            }

                            is CategoryGroupMode.Navigable -> {
                                onCategoryClick(category)
                            }
                        }
                    }
                )
            }
        }
    }
}
data class Category2(
    val title: String,
    val image: Painter? = null,
    val isSelected: Boolean = false
)

fun getGamesCategories2() : List<Category2> {
    return listOf(
        Category2(
            title = "League of Legends",
            image = null
        ),
        Category2(
            title = "DOTA 2",
            image = null
        ),
        Category2(
            title = "Heroes Of the Storm",
            image = null
        ),
        Category2(
            title = "Vain Glory",
            image = null
        ),
    )
}

fun getPlatformCategories2() : List<Category2> {
    return listOf(
        Category2(
            title = "PC",
            image = null
        ),
        Category2(
            title = "Xbox",
            image = null
        ),
        Category2(
            title = "Playstation",
            image = null
        ),
        Category2(
            title = "Nintendo Switch",
            image = null
        ),
    )
}

fun getContentCategories2(): List<Category2> {
    return listOf(
        Category2(
            title = "Game Reviews",
            image = null
        ),
        Category2(
            title = "Patch Notes/Updates",
            image = null
        ),
        Category2(
            title = "Upcoming Releases",
            image = null
        ),
        Category2(
            title = "Developer Interviews",
            image = null
        ),
        Category2(
            title = "Game Guides/Tutorials",
            image = null
        ),
        Category2(
            title = "Deals & Discounts",
            image = null
        )
    )
}
