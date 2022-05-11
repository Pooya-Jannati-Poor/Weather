package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelCurrentTemperature(

    @SerializedName("Metric")
    val temperatureInCelsius: ModelTemperatureDetail

)