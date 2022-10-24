package com.example.trabajopractico

import com.j256.ormlite.field.DatabaseField
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonajeData(
    @DatabaseField(id = true)
    var id : Int = 0,
    @DatabaseField
    var url : String="",
    @DatabaseField
    var name : String="",
    @DatabaseField
    var gender : String="",
    @DatabaseField
    var culture : String="",
    @DatabaseField
    var born : String="",
    @DatabaseField
    var died : String="",
    @DatabaseField
    var titlesString : String = "",
    var titles : List<String> = ArrayList(),
    @DatabaseField
    var aliasesString : String = "",
    var aliases : List<String> = ArrayList(),
    @DatabaseField
    var father : String = "",
    @DatabaseField
    var mother : String = "",
    @DatabaseField
    var spouse : String = "",
    @DatabaseField
    var allegiancesString : String = "",
    var allegiances : List<String> = ArrayList(),
    @DatabaseField
    var booksString : String = "",
    var books : List<String> = ArrayList(),
    @DatabaseField
    var tvSeriesString : String = "",
    var tvSeries : List<String> = ArrayList(),
    @DatabaseField
    var playedByString : String = "",
    var playedBy : List<String> = ArrayList(),
    @DatabaseField
    var page : Int = 0,
)
