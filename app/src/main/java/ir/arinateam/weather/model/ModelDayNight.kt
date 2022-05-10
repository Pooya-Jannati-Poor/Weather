package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelDayNight(

    @SerializedName("ShortPhrase")
    val shortPhrase: String,

    @SerializedName("LongPhrase")
    val longPhrase: String,

    @SerializedName("PrecipitationProbability")
    val precipitationProbability: Int,

    @SerializedName("ThunderstormProbability")
    val thunderstormProbability: Int,

    @SerializedName("RainProbability")
    val rainProbability: Int,

    @SerializedName("SnowProbability")
    val snowProbability: Int,

    @SerializedName("Wind")
    val wind: ModelWind,

    @SerializedName("WindGust")
    val windGust: ModelWindGust,

    @SerializedName("CloudCover")
    val cloudCover: Int,



)
