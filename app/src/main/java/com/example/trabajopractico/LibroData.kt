package com.example.trabajopractico

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@DatabaseTable(tableName = "librodata")
data class LibroData(
    @DatabaseField(id = true)
    var id : Int = 0,
    @DatabaseField
    var url : String = "",
    @DatabaseField
    var name : String = "",
    @DatabaseField
    var isbn : String = "",
    @DatabaseField
    var authorsString : String = "",
    var authors : List<String> = ArrayList(),
    @DatabaseField
    var numberOfPages :  Int = 0,
    @DatabaseField
    var publisher : String = "",
    @DatabaseField
    var country : String = "",
    @DatabaseField
    var mediaType : String = "",
    @DatabaseField
    var released : String = "",
    @DatabaseField
    var charactersString : String = "",
    var characters : List<String> = ArrayList(),
    @DatabaseField
    var povCharactersString : String = "",
    var povCharacters : List<String> = ArrayList(),
    @DatabaseField
    var page : Int = 0,
)
