package com.githubapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.githubapp.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavController
) {
    var search by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Card(
                modifier = Modifier
                    .align(Alignment.Center),
                shape = MaterialTheme.shapes.small,
                elevation = 8.dp
            ) {
                Column() {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                            .size(36.dp),
                        tint = MaterialTheme.colors.primary.copy(0.8f)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        value = search,
                        onValueChange = { search = it },
                        label = {
                            Text(text = stringResource(R.string.name))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                keyboardController?.hide()
                                if (search != "") {
                                    navController.navigate("search_results/${search}")
                                } else {
                                    Toast.makeText(
                                        context,
                                        R.string.please_write_name,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    )
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        onClick = {
                            if (search != "") {
                                navController.navigate("search_results/${search}")
                            } else {
                                Toast.makeText(context, R.string.please_write_name, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                    ) {
                        Text(stringResource(R.string.search))
                    }
                }
            }
        }
    }
}