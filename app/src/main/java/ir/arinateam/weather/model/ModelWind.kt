package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelWind(

    @SerializedName("Speed")
    val speed: ModelWindSpeed,

    @SerializedName("Direction")
    val direction: ModelWindDirection

)