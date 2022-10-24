package com.example.trabajopractico.cache

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "personajepage")
data class PersonajePage(
    @DatabaseField(id = true)
    var page : Int = 0
)