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

    private val _gameFilters = MutableStateFlow(GameFilter.getSwitchData())
    val gameFilters: StateFlow<List<GameFilter>> = _gameFilters

    private val _topicFilters = MutableStateFlow(TopicFilter.getSwitchData())
    val topicFilters: StateFlow<List<TopicFilter>> = _topicFilters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadUserFilters() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            when (val result = getUserFiltersUseCase.execute(input = Unit)) {
                is GetUserFiltersUseCase.Output.Success -> {
                    _isLoading.value = false
                    val filters = result.filters
                    _gameFilters.value = _gameFilters.value.map { switchData ->
                        if (filters.games.contains(switchData.gameId)) {
                            switchData.copy(isChecked = true)
                        } else {
                            switchData.copy(isChecked = false)
                        }
                    }
                    _topicFilters.value = _topicFilters.value.map { switchData ->
                        if (filters.topics.contains(switchData.topicEnum)) {
                            switchData.copy(isChecked = true)
                        } else {
                            switchData.copy(isChecked = false)
                        }
                    }
                }

                is GetUserFiltersUseCase.Output.Failure -> {
                    _errorMessage.value = result.error.message
                    _isLoading.value = false
                }
            }
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
    }

    fun toggleTopicFilter(topic: TopicEnum) {
        _topicFilters.value = _topicFilters.value.map { switchData ->
            if (switchData.topicEnum == topic) {
                switchData.copy(isChecked = !switchData.isChecked)
            } else {
                switchData
            }
        }
    }

    fun saveUserFilters() {
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
                    _isLoading.value = false
                }

                is UpdateUserFiltersUseCase.Output.Failure -> {
                    _errorMessage.value = result.error.message
                    _isLoading.value = false
                }
            }
        }
    }
}
