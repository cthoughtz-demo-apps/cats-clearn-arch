package com.simple.cats.domain.model


import com.google.gson.annotations.SerializedName

class ImageSearchResponse : ArrayList<ImageSearchResponse.ImageSearchResponseItem>(){
    data class ImageSearchResponseItem(
        @SerializedName("breeds")
        var breeds: List<Breed?>?,
        @SerializedName("categories")
        var categories: List<Any?>?,
        @SerializedName("height")
        var height: Int?,
        @SerializedName("id")
        var id: String?,
        @SerializedName("mime_type")
        var mimeType: String?,
        @SerializedName("url")
        var url: String?,
        @SerializedName("width")
        var width: Int?
    ) {
        data class Breed(
            @SerializedName("breed_group")
            var breedGroup: String?,
            @SerializedName("height")
            var height: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("life_span")
            var lifeSpan: String?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("weight")
            var weight: String?
        )
    }
}