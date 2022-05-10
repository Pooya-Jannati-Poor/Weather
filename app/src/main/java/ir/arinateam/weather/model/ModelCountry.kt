package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelCountry(

    @SerializedName("LocalizedName")
    val countryName: String

)