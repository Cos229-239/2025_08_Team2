package com.example.ravengamingnews.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
@HiltViewModel
class FiltersScreenViewModel @Inject constructor() : ViewModel()
{

    // Current selections
    private val _filteredGames = MutableStateFlow(setOf<String>())
    val filteredGames: StateFlow<Set<String>> = _filteredGames

    private val _filteredPlatforms = MutableStateFlow(setOf<String>())
    val filteredPlatforms: StateFlow<Set<String>> = _filteredPlatforms

    private val _filteredContent = MutableStateFlow(setOf<String>())
    val filteredContent: StateFlow<Set<String>> = _filteredContent

    // Saved selections
    private var savedGames = setOf<String>()
    private var savedPlatforms = setOf<String>()
    private var savedContent = setOf<String>()

    // Computed flag: any changes?
    val hasUnsavedChanges: Flow<Boolean> = combine(
        _filteredGames, _filteredPlatforms, _filteredContent
    ) { games: Set<String>, platforms: Set<String>, content: Set<String> -> games != savedGames || platforms != savedPlatforms || content != savedContent }

    // --- Update Methods ---

    fun updateGamesSelection(newSet: Set<String>) {
        _filteredGames.value = newSet
    }

    fun updatePlatformsSelection(newSet: Set<String>) {
        _filteredPlatforms.value = newSet
    }

    fun updateContentSelection(newSet: Set<String>) {
        _filteredContent.value = newSet
    }

    fun saveFilters() {
        savedGames = _filteredGames.value
        savedPlatforms = _filteredPlatforms.value
        savedContent = _filteredContent.value

        Log.d("FiltersViewModel", "Saved Games: ${savedGames.joinToString()}")
        Log.d("FiltersViewModel", "Saved Platforms: ${savedPlatforms.joinToString()}")
        Log.d("FiltersViewModel", "Saved Content: ${savedContent.joinToString()}")
    }

}