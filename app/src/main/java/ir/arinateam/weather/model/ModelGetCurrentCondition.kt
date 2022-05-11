package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelGetCurrentCondition(

    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String,

    @SerializedName("")
    val weatherText: String,

    @SerializedName("WeatherIcon")
    val weatherIcon: Int,

    @SerializedName("Temperature")
    val temperature: ModelCurrentTemperature,

    @SerializedName("Wind")
    val wind: ModelCurrentWind,

    @SerializedName("WindGust")
    val windGust: ModelCurrentWindGust

)
