package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelRealFeelTemperature(

    @SerializedName("Minimum")
    val minimum: ModelTemperatureDetail,

    @SerializedName("Maximum")
    val maximum: ModelTemperatureDetail

)
