package com.example.portfolio.data.model

import com.example.portfolio.about.Profile

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val email: String,
    val profile: Profile?
)