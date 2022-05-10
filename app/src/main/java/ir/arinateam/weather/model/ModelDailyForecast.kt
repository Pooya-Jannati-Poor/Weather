package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelDailyForecast(

    @SerializedName("Sun")
    val sunDetail: ModelSunDetail,

    @SerializedName("Moon")
    val moonDetail: ModelMoonDetail,

    @SerializedName("Temperature")
    val temperature: ModelTemperature,

    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: ModelRealFeelTemperature,

    @SerializedName("Day")
    val day: ModelDayNight,

    @SerializedName("Night")
    val night: ModelDayNight

)
