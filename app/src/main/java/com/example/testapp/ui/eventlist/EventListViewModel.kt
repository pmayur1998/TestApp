package com.example.testapp.ui.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.model.Event
import com.example.testapp.domain.model.Result
import com.example.testapp.domain.usecase.GetEventsUseCase
import com.example.testapp.domain.usecase.RefreshEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val refreshEventsUseCase: RefreshEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventListUiState>(EventListUiState.Loading)
    val uiState: StateFlow<EventListUiState> = _uiState.asStateFlow()

    init {
        refreshEvents()
        getEvents()
    }

    private fun getEvents() {
        getEventsUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Result.Success -> EventListUiState.Success(result.data)
                is Result.Error -> EventListUiState.Error(result.exception.message)
                is Result.Loading -> EventListUiState.Loading
            }
        }.launchIn(viewModelScope)
    }

    private fun refreshEvents() {
        viewModelScope.launch {
            try {
                _uiState.value = EventListUiState.Loading
                refreshEventsUseCase()
            } catch (e: Exception) {
                _uiState.value = EventListUiState.Error(e.message)
            }
        }
    }
}

sealed class EventListUiState {
    data object Loading : EventListUiState()
    data class Success(val events: List<Event>) : EventListUiState()
    data class Error(val message: String?) : EventListUiState()
}