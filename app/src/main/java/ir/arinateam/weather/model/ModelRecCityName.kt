package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelRecCityName(

    @SerializedName("Key")
    val id: Int,

    @SerializedName("LocalizedName")
    val name: String,

    @SerializedName("Country")
    val country: ModelCountry,

    @SerializedName("AdministrativeArea")
    val province: ModelProvince

)