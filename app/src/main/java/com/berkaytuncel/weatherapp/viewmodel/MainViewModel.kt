package com.berkaytuncel.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkaytuncel.weatherapp.model.WeatherModel
import com.berkaytuncel.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    private val weatherAPIService = WeatherAPIService()
    private val disposible = CompositeDisposable()

    val weatherData = MutableLiveData<WeatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoad = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
        //getDataFromLocal()
    }

    private fun getDataFromAPI(cityName: String){
        weatherLoad.value = true

        disposible.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoad.value = false
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoad.value = false
                    }
                })
        )
    }
}