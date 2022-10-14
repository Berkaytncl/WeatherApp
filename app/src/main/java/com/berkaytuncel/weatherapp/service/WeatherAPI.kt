package com.berkaytuncel.weatherapp.service

import com.berkaytuncel.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET

//https://api.openweathermap.org/data/2.5/weather?q=kocaeli&appid=23ad6da6c56b202ecc38240b92e78d2c

interface WeatherAPI {

    @GET("data/2.5/weather?q=kocaeli&appid=23ad6da6c56b202ecc38240b92e78d2c")
    fun getData(): Single<WeatherModel>
}