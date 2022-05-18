package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelGetTwelveHoursForecast(

    @SerializedName("DateTime")
    val dateTime: String,

    @SerializedName("WeatherIcon")
    val weatherIcon: Int,

    @SerializedName("Temperature")
    val modelTemperature: ModelTemperatureDetail,

    @SerializedName("Wind")
    val wind: ModelWind

)
