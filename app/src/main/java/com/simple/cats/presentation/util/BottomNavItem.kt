package com.simple.cats.presentation.util

import com.simple.cats.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object ImageSearch : BottomNavItem("imageSearch", R.drawable.baseline_search, "ImageSearch")
    object BreedSearch: BottomNavItem("breedSearch",R.drawable.breed_search, "BreedSearch")
    object BreedFacts: BottomNavItem("breedFacts",R.drawable.breed_facts, "BreedFacts")
}