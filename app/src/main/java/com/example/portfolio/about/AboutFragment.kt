package com.example.portfolio.about

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.R
import com.example.portfolio.databinding.FragmentAboutBinding

@Suppress("DEPRECATION") // issue about getParceable(): https://issuetracker.google.com/issues/240585930
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private var profile: Profile? = DEFAULT_PROFILE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater)

        arguments?.takeIf { it.containsKey(KEY_PROFILE) } ?.apply {
            profile = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelable(KEY_PROFILE, Profile::class.java)
            } else getParcelable(KEY_PROFILE)
        }

        binding.composeView.setContent {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                this.items(1){
                    profile?.let { AboutMe(it) }
                }
            }
        }
        return binding.root
    }

    @Composable
    private fun EmailIcon(email: String) {
        IconButton(
            onClick = { emailTo(email) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_email_24),
                contentDescription = "email icon")
        }
    }

    @Composable
    private fun AboutMe(profile: Profile) {
        Card(modifier = Modifier
            .padding(top = 32.dp, start = 32.dp, end = 32.dp)
            .fillMaxWidth(0.95f)) {
            Column (modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .heightIn(min = 240.dp, max = 600.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ProfilePicture(
                    imageID = profile.profilePictureID
                )
                Row {
                    Text(
                        text = profile.name,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    profile.email?.let { EmailIcon(email = it) }
                }
                Text(text = stringResource(R.string.about_me),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(text = profile.description,
                    modifier = Modifier.fillMaxWidth(0.95f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(text = stringResource(R.string.interests),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.titleMedium,
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(64.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(profile.interestList.size) { index ->
                        InterestText(profile.interestList[index])
                    }
                }
            }
        }
    }

    @Composable
    private fun InterestText(interest: Interests) {
        Button(
            onClick = { },
            modifier = Modifier
                .heightIn(max = 32.dp)
                .wrapContentWidth(),
            contentPadding = PaddingValues(start = 0.dp, end = 0.dp, bottom = 3.dp),
            shape = RoundedCornerShape(32.dp),
            enabled = false,
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.onSecondary, MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(
                text = interest.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }

    @Composable
    private fun ProfilePicture(imageID: Int) {
        Image(
            painter = painterResource(id = imageID),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape),
        )
    }

    @Preview
    @Composable
    private fun AboutPreview(){

        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            this.items(1) {
                AboutMe(profile = johnDoeProfile)
                AboutMe(profile = luisFernandoProfile)
            }
        }
    }

    private fun emailTo(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        startActivity(emailIntent)
    }

    companion object {
        val johnDoeProfile = Profile(
            R.drawable.ic_launcher_foreground,
            "Baby Shark, doo-doo, doo-doo; Baby Shark, doo-doo, doo-doo; Baby Shark, doo-doo, doo-doo; " +
                    "Baby Shark, doo-doo, doo-doo, Baby Shark. Mommy Shark, doo-doo, doo-doo...",
            "John Doe",
            listOf(
                Interests.Cooking,
                Interests.Music,
                Interests.Travel,
                Interests.Gaming,
                Interests.Tech,
                Interests.Books,
                Interests.Sports,
                Interests.Cinema
            ),
            null
        )

        val luisFernandoProfile = Profile(
           R.mipmap.luis_profile_picture_foreground,
            "\tI was born in Curitiba, grew up in Brasília and studied in UNICAMP, Campinas.\n" +
                    "\tMy love for building things led me to software, which is free from many constraints found in other engineering disciplines.\n" +
                    "\tFor me, italian food is the best. \n" +
                    "\tCurrently I'm learning to bake bread and play the guitar.",
            "Luís Fernando",
            listOf(
                Interests.Cooking,
                Interests.Music,
                Interests.Travel,
                Interests.Tech,
                Interests.Gaming
            ),
            "luisbdos.santos@gmail.com"
        )

        const val KEY_PROFILE = "profile"
        val DEFAULT_PROFILE = luisFernandoProfile

        fun newInstance(profile: Profile = DEFAULT_PROFILE): AboutFragment {
            val fragment = AboutFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(KEY_PROFILE, profile)
            }
            return fragment
        }
    }
}
