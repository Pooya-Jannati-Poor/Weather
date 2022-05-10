package ir.arinateam.weather.api

import io.reactivex.rxjava3.core.Single
import ir.arinateam.weather.model.ModelGetOneDayForecast
import ir.arinateam.weather.model.ModelRecCityName
import retrofit2.http.*

interface ApiInterface {

    @GET("locations/v1/cities/autocomplete")
    fun getCitiesName(
        @Query("apikey") apikey: String = "JrXcR2XrjVJV6SYbel6qGbyyQaW43MY2",
        @Query("q") cityName: String
    ): Single<ArrayList<ModelRecCityName>>

    @GET("forecasts/v1/daily/1day/{cityId}")
    fun getOneDayForecast(
        @Query("apikey") apikey: String = "JrXcR2XrjVJV6SYbel6qGbyyQaW43MY2",
        @Path("cityId") cityId: Int,
        @Query("details") details: Boolean = true
    ): Single<ModelGetOneDayForecast>

}