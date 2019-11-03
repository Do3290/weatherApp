package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainWeatherApi {
    @GET("weather/summary")
    fun getRequest( //weather planet skt 시간별 예보 (위도 경도)
        @Query("appKey") appKey: String?,
        @Query("version") version: Int?,
        @Query("lat")     lat: Double?,
        @Query("lon")     lon: Double?
    ): Call<MainWeatherModel>

    @GET("weather/current/minutely")
    fun getRequest2(
        @Query("appKey") appKey: String?,
        @Query("version") version: Int?,
        @Query("lat")     lat: Double?,
        @Query("lon")     lon: Double?
    ): Call<MainWeatherModel2>

    @GET("weather/forecast/3hours")
    fun getRequest3(
        @Query("appKey") appKey: String?,
        @Query("version") version: Int?,
        @Query("lat")     lat: Double?,
        @Query("lon")     lon: Double?
    ): Call<Model3>


    companion object {
        val APPKEY = "9f83e3e1-8412-4ee9-9857-97db3279aa66"
        //9f83e3e1-8412-4ee9-9857-97db3279aa66
    }
}