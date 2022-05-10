package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelGetOneDayForecast(

    @SerializedName("Headline")
    val headline: ModelHeadline,

    @SerializedName("DailyForecasts")
    val dailyForecasts: ArrayList<ModelDailyForecast>

)
