package ir.arinateam.weather.api

import io.reactivex.rxjava3.core.Single
import ir.arinateam.weather.model.ModelGetCurrentCondition
import ir.arinateam.weather.model.ModelGetFutureDayForecast
import ir.arinateam.weather.model.ModelRecCityName
import retrofit2.http.*

interface ApiInterface {

    @GET("locations/v1/cities/autocomplete")
    fun getCitiesName(
        @Query("apikey") apikey: String = "JrXcR2XrjVJV6SYbel6qGbyyQaW43MY2",
        @Query("q") cityName: String
    ): Single<ArrayList<ModelRecCityName>>

    @GET("currentconditions/v1/{cityId}")
    fun getCurrentCondition(
        @Path("cityId") cityId: Int,
        @Query("apikey") apikey: String = "JrXcR2XrjVJV6SYbel6qGbyyQaW43MY2",
        @Query("details") details: Boolean = true
    ): Single<ArrayList<ModelGetCurrentCondition>>

    @GET("forecasts/v1/daily/5day/{cityId}")
    fun getFiveDayForecast(
        @Path("cityId") cityId: Int,
        @Query("apikey") apikey: String = "JrXcR2XrjVJV6SYbel6qGbyyQaW43MY2",
        @Query("details") details: Boolean = true,
        @Query("metric") metric: Boolean = true
    ): Single<ModelGetFutureDayForecast>

}