package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelTemperatureDetail(

    @SerializedName("Value")
    val value: Int,

    @SerializedName("Unit")
    val unit: String,

    @SerializedName("UnitType")
    val valueIcon: Int,

)
