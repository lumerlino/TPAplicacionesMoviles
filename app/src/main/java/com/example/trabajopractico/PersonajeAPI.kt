package com.example.trabajopractico

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonajeAPI {
    @GET("characters/{id}")
    fun getPersonaje(@Path("id") id: Int) : Call<PersonajeData>

    @GET("characters?")
    fun getPersonajes(@Query("page") page: Int, @Query("pageSize") pageSize: Int) : Call<List<PersonajeData>>
}