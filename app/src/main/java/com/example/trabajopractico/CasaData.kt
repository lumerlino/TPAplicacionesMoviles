package com.example.trabajopractico

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@DatabaseTable(tableName = "casadata")
data class CasaData(
    @DatabaseField(id = true)
    var id : Int = 0,
    @DatabaseField
    var url : String="",
    @DatabaseField
    var name : String="",
    @DatabaseField
    var region : String="",
    @DatabaseField
    var coatOfArms : String="",
    @DatabaseField
    var words : String="",
    var titles : List<String> = ArrayList(),
    @DatabaseField
    var titlesString : String="",
    var seats : List<String> = ArrayList(),
    @DatabaseField
    var seatsString : String="",
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
    var ancestralWeapons : List<String> = ArrayList(),
    @DatabaseField
    var ancestralWeaponsString : String="",
    var cadetBranches : List<String> = ArrayList(),
    @DatabaseField
    var cadetBranchesString : String = "",
    var swornMembers : List<String> = ArrayList(),
    @DatabaseField
    var swornMembersString : String = "",
    @DatabaseField
    var page : Int=0,
)
