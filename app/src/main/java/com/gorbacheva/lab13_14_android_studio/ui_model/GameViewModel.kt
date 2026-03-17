package com.gorbacheva.lab13_14_android_studio.ui_model

import androidx.lifecycle.ViewModel
import com.gorbacheva.lab13_14_android_studio.data.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {

    }
}