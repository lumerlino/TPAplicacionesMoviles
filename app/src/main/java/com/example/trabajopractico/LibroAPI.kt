package com.example.trabajopractico

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LibroAPI {
    @GET("books/{id}")
    fun getLibro(@Path("id") id: Int) : Call<LibroData>

    @GET("books?page=1&pageSize=50")
    fun getLibros() : Call<List<LibroData>>
}