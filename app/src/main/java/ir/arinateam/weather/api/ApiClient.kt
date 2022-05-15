package ir.arinateam.weather.api

import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import ir.arinateam.weather.model.ModelGetCurrentCondition
import ir.arinateam.weather.model.ModelGetFutureDayForecast
import ir.arinateam.weather.model.ModelRecCityName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val baseUrl = "http://dataservice.accuweather.com/"

    private var request: ApiInterface

    companion object {

        lateinit var retrofit: Retrofit

    }

    init {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        request = retrofit.create(ApiInterface::class.java)
    }

    fun getCitiesName(
        cityName: String
    ): Single<ArrayList<ModelRecCityName>> {
        return request.getCitiesName(cityName = cityName)
    }

    fun getCurrentCondition(cityId: Int): Single<ArrayList<ModelGetCurrentCondition>> {
        return request.getCurrentCondition(cityId = cityId)
    }

    fun getFiveDayForecast(cityId: Int): Single<ModelGetFutureDayForecast> {
        return request.getFiveDayForecast(cityId = cityId)
    }

}