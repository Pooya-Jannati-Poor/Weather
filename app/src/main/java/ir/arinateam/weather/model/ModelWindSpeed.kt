package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelWindSpeed(

    @SerializedName("Value")
    val speedValue: Int,

    @SerializedName("Unit")
    val unit: String

)
