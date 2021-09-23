package com.example.worldbeer.ui.home.model

import com.google.gson.annotations.SerializedName

class BeerResponse(
    @SerializedName("name") val data: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("abv") val abv: String,
    @SerializedName("ibu") val ibu: String
) {
    fun toDomain(): BeerDomain {
        return BeerDomain(
            data = data,
            imageUrl = imageUrl,
            description = description,
            abv = abv,
            ibu = ibu
        )
    }
}
