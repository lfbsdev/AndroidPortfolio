package com.example.portfolio.component_catalog.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.R
import com.example.portfolio.databinding.FragmentMyModalBottomSheetBinding

class MyModalBottomSheetFragment : Fragment() {

    private lateinit var binding: FragmentMyModalBottomSheetBinding

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyModalBottomSheetBinding.inflate(layoutInflater)

        binding.bottomSheetView.setContent {
            ModalBottomSheetLayout()
        }
        return binding.root
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    @ExperimentalMaterial3Api
    fun ModalBottomSheetLayout(){
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberSheetState(skipHalfExpanded = false)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { openBottomSheet = !openBottomSheet }) {
                Text(text = "Choose Photo")
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    text = "Profile Photo",
                    style = MaterialTheme.typography.titleLarge
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(2) {
                        when(it){
                            0 -> IconButtonWithText(imageID = R.drawable.ic_baseline_insert_photo_48,
                                description = "Choose from gallery",
                                onClick = {},
                                text = "Gallery")
                            1 -> IconButtonWithText(imageID = R.drawable.ic_baseline_photo_camera_48,
                                description = "Take a photo",
                                onClick = {},
                                text = "Camera")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun IconButtonWithText(imageID: Int, onClick: () -> Unit, description: String?, text: String){
        return Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onClick
            ) {
                Image(
                    painter = painterResource(id = imageID),
                    contentDescription = description)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

    @Preview
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DefaultPreview(){
        ModalBottomSheetLayout()
    }
    companion object {
        fun newInstance() =
            MyModalBottomSheetFragment()
    }
}