package com.example.trabajopractico.cache

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "casapage")
data class CasaPage(
    @DatabaseField(id = true)
    var page : Int = 0
)
