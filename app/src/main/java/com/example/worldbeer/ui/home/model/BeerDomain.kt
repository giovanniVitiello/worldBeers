package com.example.worldbeer.ui.home.model

import com.google.gson.annotations.SerializedName

class BeerDomain(
    val name: String?,
    val imageUrl: String?,
    val description: String?,
    val abv: String?,
    val ibu: String?,
    val firstBrewed: String?,
    val foodPairing: List<String>?,
    val brewersTips: String?
)
