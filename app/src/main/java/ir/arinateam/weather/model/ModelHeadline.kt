package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelHeadline(

    @SerializedName("Text")
    val text: String,

    @SerializedName("Category")
    val category: String

)
