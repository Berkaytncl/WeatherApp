package com.berkaytuncel.weatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.berkaytuncel.weatherapp.R
import com.berkaytuncel.weatherapp.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val cName = GET.getString("cityName", "ankara")
        edt_city_name.setText(cName)

        viewModel.refreshData()

        getLiveData()
    }

    private fun getLiveData() {
        viewModel.weatherData.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE

                tv_degree.text = data.main.temp.toString()

                tv_country_code.text = data.sys.country
                tv_city_name.text = data.name

                tv_humidity.text = data.main.humidity.toString()
                tv_speed.text = data.wind.speed.toString()
                tv_lat.text = data.coord.lat.toString()
                tv_lon.text = data.coord.lon.toString()

                Glide.with(this)
                    .load("http://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(img_weather_icon)
            }
        })

        viewModel.weatherLoad.observe(this, Observer { load ->
            load?.let{
                if(it){
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                }else{
                    pb_loading.visibility = View.GONE
                }
            }
        })

        viewModel.weatherError.observe(this, Observer { error ->
            error?.let{
                if(it){
                    tv_error.visibility = View.VISIBLE
                    ll_data.visibility = View.GONE
                    pb_loading.visibility = View.GONE
                }else{
                    tv_error.visibility = View.GONE
                }
            }
        })
    }
}