package com.example.trabajopractico

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LibroData(
    var url : String,
    var name : String,
    var isbn : String,
    var numberOfPages :  Int,
    var publisher : String,
    var country : String,
    var mediaType : String,
    var released : String
)
