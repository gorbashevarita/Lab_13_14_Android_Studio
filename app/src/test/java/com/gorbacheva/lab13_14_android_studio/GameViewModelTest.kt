package com.gorbacheva.lab13_14_android_studio

import androidx.lifecycle.viewmodel.compose.viewModel
import com.gorbacheva.lab13_14_android_studio.data.MAX_NO_OF_WORDS
import com.gorbacheva.lab13_14_android_studio.data.SCORE_INCREASE
import com.gorbacheva.lab13_14_android_studio.ui_model.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    private fun getUnscrambledWord(scrambledWord: String): String {
        return com.gorbacheva.lab13_14_android_studio.data.allWords.firstOrNull { word ->
            scrambledWord.toSet() == word.toSet()
        } ?: ""
    }

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()
        currentGameUiState = viewModel.uiState.value
        assertEquals(SCORE_INCREASE, currentGameUiState.score)
        assertFalse(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "incorrect"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()
        val currentGameUiState = viewModel.uiState.value
        assertEquals(0, currentGameUiState.score)
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unscrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)
        assertNotEquals(unscrambledWord, gameUiState.currentScrambledWord);
        assertTrue(gameUiState.currentWordCount == 1);
        assertTrue(gameUiState.score == 0);
        assertFalse(gameUiState.isGameOver);

    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            assertEquals(expectedScore, currentGameUiState.score)
        }
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        assertTrue(currentGameUiState.isGameOver)

    }
}