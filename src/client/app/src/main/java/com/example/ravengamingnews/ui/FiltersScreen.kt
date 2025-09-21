package com.example.ravengamingnews.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ravengamingnews.R
import com.example.ravengamingnews.roundedCornerSize
import com.example.ravengamingnews.ui.theme.RavenGamingNewsTheme




@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier,
    viewModel: FiltersScreenViewModel = hiltViewModel()
) {
    val filteredGames by viewModel.filteredGames.collectAsState()
    val filteredPlatforms by viewModel.filteredPlatforms.collectAsState()
    val filteredContent by viewModel.filteredContent.collectAsState()
    val hasUnsavedChanges by viewModel.hasUnsavedChanges.collectAsState(false)

    Box {
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
                        filteredItems = filteredGames,
                        onFilterChanged = { viewModel.updateGamesSelection(it) }
                    )
                }
                item {
                    CategoryGroupPR2(
                        title = "Platforms",
                        categories = getPlatformCategories2(),
                        filteredItems = filteredPlatforms,
                        onFilterChanged = { viewModel.updatePlatformsSelection(it) }
                    )
                }
                item {
                    CategoryGroupPR2(
                        title = "Content",
                        categories = getContentCategories2(),
                        filteredItems = filteredContent,
                        onFilterChanged = { viewModel.updateContentSelection(it) }
                    )
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                AnimatedVisibility(
                    visible = hasUnsavedChanges,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                )
                {
                    Button(
                        onClick = { viewModel.saveFilters() },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp,
                            hoveredElevation = 6.dp,
                            focusedElevation = 6.dp
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(75.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            "Save button",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }

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
            .background(
                Brush.verticalGradient(
                    colorStops = colorStops
                )
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
    filteredItems: Set<String>,
    onFilterChanged: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
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
                val isSelected = filteredItems.contains(category.title)

                CategoryButtonPR2(
                    category = category.copy(isSelected = isSelected),
                    onClick = {
                                val updatedSet = if (isSelected){
                                    filteredItems - category.title
                                } else {
                                    filteredItems + category.title
                                }
                                onFilterChanged(updatedSet)
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
