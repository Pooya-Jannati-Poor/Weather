package ir.arinateam.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.LayoutRecTwelveHoursForecastBinding
import ir.arinateam.weather.model.ModelGetTwelveHoursForecast

class AdapterRecTwelveHoursForecast(
    private val context: Context,
    private val lsModelGetTwelveHoursForecast: ArrayList<ModelGetTwelveHoursForecast>
) : RecyclerView.Adapter<AdapterRecTwelveHoursForecast.ItemAdapter>() {

    private lateinit var bindingAdapter: LayoutRecTwelveHoursForecastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter {
        val inflater = LayoutInflater.from(context)
        bindingAdapter = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_rec_twelve_hours_forecast,
            parent,
            false
        )
        return ItemAdapter(bindingAdapter)
    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {

        val tempModel = lsModelGetTwelveHoursForecast[position]

        holder.tvTime.text = tempModel.dateTime.substring(11, 16)
        holder.tvTemperature.text = tempModel.modelTemperature.value.toString()


        val currentTemperatureIcon = tempModel.weatherIcon
        holder.imgTemperatureIcon.setBackgroundResource(
            context.resources.getIdentifier(
                "s_$currentTemperatureIcon",
                "drawable",
                context.packageName
            )
        )

        holder.imgWindDirection.rotation = tempModel.wind.direction.degrees.toFloat()
        holder.tvWindSpeed.text = tempModel.wind.speed.speedValue.toString()

    }

    override fun getItemCount(): Int = lsModelGetTwelveHoursForecast.size

    inner class ItemAdapter(binding: LayoutRecTwelveHoursForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvTime = binding.tvTime
        val tvTemperature = binding.tvTemperature
        val imgTemperatureIcon = binding.imgTemperatureIcon
        val imgWindDirection = binding.imgWindDirection
        val tvWindSpeed = binding.tvWindSpeed

    }

}