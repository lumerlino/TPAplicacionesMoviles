package com.example.trabajopractico

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface YESNOAPI {
    @GET("api")
    fun getYESNO() : Call<YESNOData>
}