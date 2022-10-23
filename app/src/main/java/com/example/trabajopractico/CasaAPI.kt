package com.example.trabajopractico

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CasaAPI {
    @GET("houses/{id}")
    fun getCasa(@Path("id") id: Int) : Call<CasaData>

    @GET("houses?")
    fun getCasas(@Query("page") page: Int, @Query("pageSize") pageSize: Int) : Call<List<CasaData>>
}