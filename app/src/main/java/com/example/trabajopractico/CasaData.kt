package com.example.trabajopractico

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@DatabaseTable(tableName = "casadata")
data class CasaData(
    @DatabaseField(id = true)
    var url : String="",
    @DatabaseField
    var name : String="",
    @DatabaseField
    var region : String="",
    @DatabaseField
    var coatOfArms : String="",
    @DatabaseField
    var words : String="",
    //var titles : List<String>,
    //var seats : List<String>,
    @DatabaseField
    var currentLord : String="",
    @DatabaseField
    var heir : String="",
    @DatabaseField
    var overlord : String="",
    @DatabaseField
    var founded : String="",
    @DatabaseField
    var founder : String="",
    @DatabaseField
    var diedOut : String="",
    //var ancestralWeapons : List<String>,
    //var cadetBranches : List<String>,
    //var swornMembers : List<String>,
    @DatabaseField
    var page : Int=0,
)
