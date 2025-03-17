package com.simple.cats.presentation.views.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.simple.cats.domain.model.ImageSearchResponse
import com.simple.cats.presentation.util.CatResult
import com.simple.cats.presentation.viewmodel.ImageSearchViewModel

private const val TAG = "ImageSearchPage"

@Composable
fun ImageSearchPage(imageSearchViewModel: ImageSearchViewModel) {

    GetData(imageSearchViewModel)
}

@Composable
private fun GetData(imageSearchViewModel: ImageSearchViewModel) {

    val imageSearchResult = imageSearchViewModel.imageSearchResultFlow.collectAsStateWithLifecycle()

    when (val imageSearchData = imageSearchResult.value) {
        is CatResult.Failure -> Text(text = imageSearchData.errorMessage)
        is CatResult.Loading -> CircularProgressIndicator()
        is CatResult.Success -> {
            Log.d(TAG, "GetData: ${imageSearchData.data}")
            ShowDataOnScreen(imageSearchData.data, imageSearchViewModel)
        }

        else -> Log.d(TAG, "GetData: Something Went Wrong")
    }
}

@Composable
fun ShowDataOnScreen(
    data: ImageSearchResponse,
    imageSearchViewModel: ImageSearchViewModel
) {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            data[0].width?.dp?.let { width ->
                data[0].height?.dp?.let { height ->
                    Modifier.size(
                        width = width,
                        height = height
                    )
                }
            }?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = data[0].url),
                    contentDescription = "Cat Image",
                    modifier = it,
                    contentScale = ContentScale.Crop
                )
            }

            Button(onClick = { imageSearchViewModel.getImageSearch() }) {
                Text(text = "Generate Random Image")
            }
        }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ImageSearchPagePreview(modifier: Modifier = Modifier) {
    ImageSearchPage(imageSearchViewModel = hiltViewModel<ImageSearchViewModel>())
}