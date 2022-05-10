package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelMoonDetail(

    @SerializedName("Rise")
    val rise: String,

    @SerializedName("Set")
    val set: String

)
