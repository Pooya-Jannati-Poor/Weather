package ir.arinateam.weather.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.LayoutRecFiveDayForecastBinding
import ir.arinateam.weather.model.ModelDailyForecast
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterRecFiveDayForecast(
    private val context: Context,
    private val lsModelDailyForecastFragment: ArrayList<ModelDailyForecast>
) : RecyclerView.Adapter<AdapterRecFiveDayForecast.ItemAdapter>() {

    private lateinit var bindingAdapter: LayoutRecFiveDayForecastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter {
        val layoutInflater = LayoutInflater.from(context)
        bindingAdapter = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_rec_five_day_forecast,
            parent,
            false
        )
        return ItemAdapter(bindingAdapter)
    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {

        val tempModel = lsModelDailyForecastFragment[position]


        val calendar: Calendar = Calendar.getInstance()
        calendar.time = convertStringToDate(tempModel.forecastDate)
        val dayInMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val dayInWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
        val month: Int = calendar.get(Calendar.MONTH)

        val dayName = getDayName(dayInWeek)
        holder.tvDayName.text = dayName

        holder.tvDate.text = dayInMonth.toString().plus("/").plus(month)
        holder.imgDayIcon.setBackgroundResource(
            context.resources.getIdentifier(
                "s_${tempModel.day.icon}",
                "drawable",
                context.packageName
            )
        )
        holder.tvMaxTemperature.text = tempModel.temperature.maximum.value.toString().plus("˚")
        holder.tvMinTemperature.text = tempModel.temperature.minimum.value.toString().plus("˚")
        holder.imgNightIcon.setBackgroundResource(
            context.resources.getIdentifier(
                "s_${tempModel.night.icon}",
                "drawable",
                context.packageName
            )
        )

        val averageWindSpeed = splitWindDirectionWithTwoDecimal(
            getAverageWindSpeed(
                tempModel.day.wind.speed.speedValue,
                tempModel.night.wind.speed.speedValue
            ).toFloat()
        )

        holder.tvWindSpeed.text = averageWindSpeed

        holder.imgWindDirection.rotation = getAverageWindDirection(
            tempModel.day.wind.direction.degrees,
            tempModel.night.wind.direction.degrees
        )

    }

    private fun getDayName(dayInInt: Int): String {

        when (dayInInt) {
            Calendar.SATURDAY -> {
                return "Sat"
            }
            Calendar.SUNDAY -> {
                return "Sun"
            }
            Calendar.MONDAY -> {
                return "Mon"
            }
            Calendar.TUESDAY -> {
                return "Tue"
            }
            Calendar.WEDNESDAY -> {
                return "Wed"
            }
            Calendar.THURSDAY -> {
                return "Thu"
            }
            Calendar.FRIDAY -> {
                return "Fri"
            }
        }

        return "invalid Day"

    }

    private fun convertStringToDate(dateInString: String): Date {

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val text = dateInString.substring(0, 10)
        Log.d("substringDate", text)
        return formatter.parse(text)!!

    }

    private fun getAverageWindSpeed(minSpeed: Float, maxSpeed: Float): String {
        return ((minSpeed + maxSpeed) / 2).toString()
    }

    private fun getAverageWindDirection(dayDegree: Int, nightDegree: Int): Float {
        return ((dayDegree + nightDegree) / 2).toFloat()
    }

    private fun splitWindDirectionWithTwoDecimal(windDirection: Float): String {

        val decimalFormat = DecimalFormat("#.#")
        decimalFormat.roundingMode = RoundingMode.DOWN
        return decimalFormat.format(windDirection)

    }

    override fun getItemCount(): Int = lsModelDailyForecastFragment.size

    inner class ItemAdapter(binding: LayoutRecFiveDayForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvDayName: TextView = binding.tvDayName
        val tvDate: TextView = binding.tvDate
        val imgDayIcon: ImageView = binding.imgDayIcon
        val tvMaxTemperature: TextView = binding.tvMaxTemperature
        val tvMinTemperature: TextView = binding.tvMinTemperature
        val imgNightIcon: ImageView = binding.imgNightIcon
        val imgWindDirection: ImageView = binding.imgWindDirection
        val tvWindSpeed: TextView = binding.tvWindSpeed

    }

}