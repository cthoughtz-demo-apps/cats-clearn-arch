package com.simple.cats.domain.model


import com.google.gson.annotations.SerializedName

class BreedFactsResponse : ArrayList<BreedFactsResponse.BreedFactsResponseItem>(){
    data class BreedFactsResponseItem(
        @SerializedName("breed_id")
        var breedId: String?,
        @SerializedName("fact")
        var fact: String?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("title")
        var title: String?
    )
}