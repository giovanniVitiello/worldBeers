package com.example.worldbeers.ui.home.model

import com.google.gson.annotations.SerializedName

class BeerResponse(
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("abv") val abv: String,
    @SerializedName("ibu") val ibu: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    @SerializedName("food_pairing") val foodPairing: List<String>,
    @SerializedName("brewers_tips") val brewersTips: String
) {
    fun toDomain(): BeerDomain {
        return BeerDomain(
            name = name,
            imageUrl = imageUrl,
            description = description,
            abv = abv,
            ibu = ibu,
            firstBrewed = firstBrewed,
            foodPairing = foodPairing,
            brewersTips = brewersTips
        )
    }
}
