package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelSunDetail(

    @SerializedName("Rise")
    val rise: String,

    @SerializedName("Set")
    val set: String

)
