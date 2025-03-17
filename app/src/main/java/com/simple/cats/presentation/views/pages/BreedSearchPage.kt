package com.simple.cats.presentation.views.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simple.cats.R
import com.simple.cats.domain.model.BreedSearchResponse
import com.simple.cats.presentation.util.CatResult
import com.simple.cats.presentation.viewmodel.BreedViewModel
import androidx.constraintlayout.compose.Dimension

private const val TAG = "BreedSearchPage"

@Composable
fun BreedSearchPage(breedViewModel: BreedViewModel) {
    GetData(breedViewModel)
}

@Composable
private fun GetData(breedViewModel: BreedViewModel) {

    val breedResult = breedViewModel.breedSearchResultFlow.collectAsStateWithLifecycle()
    var showListState by remember { mutableStateOf(false) }
    when (val breedData = breedResult.value) {
        is CatResult.Failure -> Text(text = breedData.errorMessage)
        is CatResult.Loading -> CircularProgressIndicator()
        is CatResult.Success -> {
            Log.d(TAG, "GetData -- BreedSearch: ${breedData.data}")
            if (breedData.data.isNotEmpty()) {
                showListState = true
            } else {
                showListState = false
            }
            ShowDataOnScreen(breedData.data, breedViewModel, showListState)
        }

        else -> Log.d(TAG, "GetData: Soemthing Went Wrong")
    }
}


@Composable
private fun ShowDataOnScreen(data: BreedSearchResponse, breedViewModel: BreedViewModel, showListState: Boolean) {

    var searchResults by remember { mutableStateOf("") }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (searchBarId, lazyColumnId) = createRefs()

        SearchBar(
            onSearch = { query ->
                searchResults = "You search for: $query"
            },
            modifier = Modifier.constrainAs(searchBarId) {
                top.linkTo(parent.top, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            breedViewModel
        )

        if (showListState) {
            LazyColumnByName(
                data,
                modifier = Modifier.constrainAs(lazyColumnId) {
                    top.linkTo(searchBarId.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    width = Dimension.fillToConstraints // Equivalent to 0.dp for width
                    height = Dimension.fillToConstraints // Equivalent to 0.dp for height
                }
            )
        }
    }
}

@Composable
fun LazyColumnByName(data: BreedSearchResponse, modifier: Modifier = Modifier) {

    val uriHandler = LocalUriHandler.current

    data?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    2.dp, color = colorResource(id = R.color.dark_purple),
                    RoundedCornerShape(16.dp)
                )
                .background(color = Color.White)
        ) {
            items(it) {
                Column {
                    Text(
                        text = it.name ?: "",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp).clickable { uriHandler.openUri(it.vcahospitalsUrl.toString()) },
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.dark_purple)
                    )
                    Text(
                        text = it.id ?: "",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 25.dp).clickable { uriHandler.openUri(it.vcahospitalsUrl.toString()) },
                        color = colorResource(id = R.color.pink)
                    )
                }
            }
        }
    }
}


@Composable
private fun SearchBar(onSearch: (String) -> Unit, modifier: Modifier,breedViewModel: BreedViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(text = "Search...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                searchQuery?.let{ query ->
                    breedViewModel.getBreedSearch(query)
                    onSearch(query)
                    Log.d(TAG, "SearchBar: $query")
                }
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BreedSearchPagePreview() {
    BreedSearchPage(breedViewModel = hiltViewModel<BreedViewModel>())
}