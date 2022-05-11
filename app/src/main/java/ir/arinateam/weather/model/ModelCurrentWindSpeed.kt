package ir.arinateam.weather.model

import com.google.gson.annotations.SerializedName

data class ModelCurrentWindSpeed(

    @SerializedName("Metric")
    val speedValueMetric: ModelTemperatureDetail

)
