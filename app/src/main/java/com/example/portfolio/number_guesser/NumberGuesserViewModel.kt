package com.example.portfolio.number_guesser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import kotlin.random.Random

private const val DEFAULT_MIN_NUMBER = 1
private const val DEFAULT_MAX_NUMBER = 20

data class NumberGuesserState(
    val minNumber: Int = DEFAULT_MIN_NUMBER,
    val maxNumber: Int = DEFAULT_MAX_NUMBER,
    val chosenNumber: Int = Random.nextInt(DEFAULT_MIN_NUMBER, DEFAULT_MAX_NUMBER),
    val attempts: Int = 0,
    val hit: Boolean = false,
    val code: Int = NumberGuesserViewModel.STARTED_CODE
    )

class NumberGuesserViewModel : ViewModel() {
    private val _ngState = MutableStateFlow(NumberGuesserState())
    val ngState: StateFlow<NumberGuesserState> = _ngState.asStateFlow()

    fun guessNumber(number: Int) {
        if (number == ngState.value.chosenNumber) {
            _ngState.update {
                it.copy(
                    minNumber = number,
                    maxNumber = number,
                    attempts = it.attempts + 1,
                    hit = true,
                    code = ENDED_CODE
                )
            }
        }
        else if (number > ngState.value.chosenNumber) {
            _ngState.update {
                it.copy(
                    attempts = it.attempts + 1,
                    maxNumber = number - 1,
                    code = MISS_CODE
                )
            }
        }
        else if (number < ngState.value.chosenNumber) {
            _ngState.update {
                it.copy(
                    attempts = it.attempts + 1,
                    minNumber = number + 1,
                    code = MISS_CODE
                )
            }
        }
    }

    fun restart() {
        _ngState.update {
            NumberGuesserState()
        }
    }

    companion object{
        const val STARTED_CODE = -1
        const val ENDED_CODE = 1
        const val MISS_CODE = 0
    }
}