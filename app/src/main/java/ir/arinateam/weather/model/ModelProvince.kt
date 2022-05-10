package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelProvince(

    @SerializedName("LocalizedName")
    val provinceName: String

)