package com.example.portfolio.about
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(val profilePictureID: Int, val description: String, val name: String, val interestList : List<Interests>,
val email: String?) : Parcelable
