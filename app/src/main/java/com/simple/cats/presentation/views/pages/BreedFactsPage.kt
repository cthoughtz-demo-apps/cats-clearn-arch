package com.simple.cats.presentation.views.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simple.cats.R
import com.simple.cats.domain.model.BreedFactsResponse
import com.simple.cats.presentation.util.CatResult
import com.simple.cats.presentation.viewmodel.BreedFactsViewModel
import com.simple.cats.presentation.viewmodel.BreedViewModel

private const val TAG = "BreedFactsPage"

@Composable
fun BreedFactsPage(breedFactsViewModel: BreedFactsViewModel) {
    GetData(breedFactsViewModel)
}

@Composable
private fun GetData(breedFactsViewModel: BreedFactsViewModel) {

    val breedFactResult = breedFactsViewModel.breedFactsResultFlow.collectAsStateWithLifecycle()
    var showListState by remember { mutableStateOf(false) }
    when (val breedFactsData = breedFactResult.value) {
        is CatResult.Failure -> Text(text = breedFactsData.errorMessage)
        is CatResult.Loading -> CircularProgressIndicator()
        is CatResult.Success -> {
            Log.d(TAG, "GetData: BreedFacts: ${breedFactsData.data}")
            if (breedFactsData.data.isNotEmpty()) {
                showListState = true
            } else {
                showListState = false
            }
            ShowDataOnScreen(breedFactsData.data, breedFactsViewModel, showListState)
        }

        else -> Log.d(TAG, "GetData: Something Went Wrong")
    }
}

@Composable
private fun ShowDataOnScreen(
    breedFactsData: BreedFactsResponse,
    breedFactsViewModel: BreedFactsViewModel,
    showListState: Boolean
) {
    var searchResults by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (searchBId, lazyCId) = createRefs()

        SearchBarBreedFacts(
            onSearch = { query ->
                searchResults = "You search for: $query"
            },
            modifier = Modifier.constrainAs(searchBId) {
                top.linkTo(parent.top, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            breedFactsViewModel
        )

    if (showListState) {
        LazyColumnFacts(
            breedFactsData,
            modifier = Modifier.constrainAs(lazyCId) {
                top.linkTo(searchBId.bottom, margin = 10.dp)
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
fun LazyColumnFacts(data: BreedFactsResponse, modifier: Modifier = Modifier) {

    data?.let {

        LazyColumn(
            modifier
                .fillMaxSize()

        ) {
            items(it) {
                Text(
                    text = it.fact.toString(),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = colorResource(id = R.color.dark_purple),
                    modifier = modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBarBreedFacts(
    onSearch: (String) -> Unit,
    modifier: Modifier,
    breedFactsViewModel: BreedFactsViewModel
) {

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
                searchQuery?.let { query ->

                    breedFactsViewModel.getBreed(query.toInt())
                    onSearch(query)
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
fun BreedFactsPagePreview(modifier: Modifier = Modifier) {
    // BreedFactsPage()
}