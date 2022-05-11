package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelCurrentWind(

    @SerializedName("Speed")
    val speed: ModelCurrentWindSpeed,

    @SerializedName("Direction")
    val direction: ModelWindDirection

)