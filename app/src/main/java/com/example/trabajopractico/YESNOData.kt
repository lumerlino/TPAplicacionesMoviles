package com.example.trabajopractico

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YESNOData(
    var answer : String = "",
    var forced : Boolean= false,
    var image : String= ""
)
