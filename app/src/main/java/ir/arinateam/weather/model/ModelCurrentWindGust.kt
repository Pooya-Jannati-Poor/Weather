package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelCurrentWindGust(

    @SerializedName("Speed")
    val speed: ModelCurrentTemperature

)
