package com.example.ravengamingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ravengamingnews.data.GameFilter
import com.example.ravengamingnews.data.TopicEnum
import com.example.ravengamingnews.data.TopicFilter
import com.example.ravengamingnews.domain.model.UserFilters
import com.example.ravengamingnews.domain.usecase.GetUserFiltersUseCase
import com.example.ravengamingnews.domain.usecase.UpdateUserFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val getUserFiltersUseCase: GetUserFiltersUseCase,
    private val saveUserFiltersUseCase: UpdateUserFiltersUseCase,
) : ViewModel() {

    private val _originalGameFilters = MutableStateFlow(GameFilter.getSwitchData())
    private val _gameFilters = MutableStateFlow(GameFilter.getSwitchData())
    val gameFilters: StateFlow<List<GameFilter>> = _gameFilters

    private val _originalTopicFilters = MutableStateFlow(TopicFilter.getSwitchData())
    private val _topicFilters = MutableStateFlow(TopicFilter.getSwitchData())
    val topicFilters: StateFlow<List<TopicFilter>> = _topicFilters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _hasUnsavedChanges = MutableStateFlow(false)
    val hasUnsavedChanges: StateFlow<Boolean> = _hasUnsavedChanges

    fun loadUserFilters() {
        _isLoading.value = true
        _hasUnsavedChanges.value = false
        _errorMessage.value = null
        viewModelScope.launch {
            when (val result = getUserFiltersUseCase.execute(input = Unit)) {
                is GetUserFiltersUseCase.Output.Success -> {
                    val filters = result.filters
                    _originalGameFilters.value = _originalGameFilters.value.map { switchData ->
                        if (filters.games.contains(switchData.gameId)) {
                            switchData.copy(isChecked = true)
                        } else {
                            switchData.copy(isChecked = false)
                        }
                    }
                    _gameFilters.value = _originalGameFilters.value
                    _originalTopicFilters.value = _originalTopicFilters.value.map { switchData ->
                        if (filters.topics.contains(switchData.topicEnum)) {
                            switchData.copy(isChecked = true)
                        } else {
                            switchData.copy(isChecked = false)
                        }
                    }
                    _topicFilters.value = _originalTopicFilters.value
                }

                is GetUserFiltersUseCase.Output.Failure -> {
                    _errorMessage.value = result.error.message
                }
            }
            kotlinx.coroutines.delay(800)
            _isLoading.value = false
            _hasUnsavedChanges.value = hasChanges()
        }
    }

    fun toggleGameFilter(gameId: Int) {
        _gameFilters.value = _gameFilters.value.map { switchData ->
            if (switchData.gameId == gameId) {
                switchData.copy(isChecked = !switchData.isChecked)
            } else {
                switchData
            }
        }
        _hasUnsavedChanges.value = hasChanges()
    }

    fun toggleTopicFilter(topic: TopicEnum) {
        _topicFilters.value = _topicFilters.value.map { switchData ->
            if (switchData.topicEnum == topic) {
                switchData.copy(isChecked = !switchData.isChecked)
            } else {
                switchData
            }
        }
        _hasUnsavedChanges.value = hasChanges()
    }

    fun saveUserFilters() {
        _isSaving.value = true
        _errorMessage.value = null
        val filters = UserFilters(
            games = _gameFilters.value.filter { it.isChecked }.map { it.gameId },
            topics = _topicFilters.value.filter { it.isChecked }.map { it.topicEnum }
        )
        viewModelScope.launch {
            when (val result =
                saveUserFiltersUseCase.execute(input = UpdateUserFiltersUseCase.Input(filters))) {
                is UpdateUserFiltersUseCase.Output.Success -> {
                    // Successfully saved filters
                    _errorMessage.value = null
                    _originalGameFilters.value = _gameFilters.value
                    _originalTopicFilters.value = _topicFilters.value
                }

                is UpdateUserFiltersUseCase.Output.Failure -> {
                    _errorMessage.value = result.error.message
                }
            }
            kotlinx.coroutines.delay(800)
            _isSaving.value = false
            _hasUnsavedChanges.value = hasChanges()
        }
    }

    private fun hasChanges(): Boolean {
        val currentGameFilters = _gameFilters.value
        val currentTopicFilters = _topicFilters.value
        return currentGameFilters != _originalGameFilters.value ||
               currentTopicFilters != _originalTopicFilters.value
    }
}
