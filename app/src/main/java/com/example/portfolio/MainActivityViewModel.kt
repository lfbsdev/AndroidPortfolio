package com.example.portfolio

import androidx.lifecycle.ViewModel
import com.example.portfolio.about.AboutFragment
import com.example.portfolio.about.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainActivityState(
    val currentProfile: Profile = AboutFragment.luisFernandoProfile
)

class MainActivityViewModel : ViewModel() {
    private val _mainActivityState = MutableStateFlow(MainActivityState())
    val mainActivityState: StateFlow<MainActivityState> = _mainActivityState.asStateFlow()
}