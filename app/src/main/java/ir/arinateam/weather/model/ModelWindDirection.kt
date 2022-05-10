package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelWindDirection(

    @SerializedName("Degrees")
    val degrees: Int,

    @SerializedName("Localized")
    val localized: String,

)
