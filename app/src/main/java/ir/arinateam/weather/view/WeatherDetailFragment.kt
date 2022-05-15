package ir.arinateam.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.WeatherDetailFragmentBinding
import ir.arinateam.weather.model.ModelGetCurrentCondition
import ir.arinateam.weather.viewmodel.ViewModelWeatherDetailFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class WeatherDetailFragment : Fragment() {

    private lateinit var bindingFragment: WeatherDetailFragmentBinding

    private lateinit var tvMinMaxTemperature: TextView
    private lateinit var imgCurrentTemperatureIcon: ImageView
    private lateinit var tvCurrentTimeTemperature: TextView
    private lateinit var tvCurrentDayConditionName: TextView
    private lateinit var tvRainPercent: TextView
    private lateinit var tvCurrentWindSpeed: TextView
    private lateinit var tvCurrentWindDirection: TextView
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvCurrentDate: TextView
    private lateinit var btnChangeFragmentFiveDayForecast: Button

    private lateinit var viewModel: ViewModelWeatherDetailFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.weather_detail_fragment, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        getCityIdFromBundle()

    }

    private fun initView() {

        tvMinMaxTemperature = bindingFragment.tvMinMaxTemperature
        imgCurrentTemperatureIcon = bindingFragment.imgCurrentTemperatureIcon
        tvCurrentTimeTemperature = bindingFragment.tvCurrentTimeTemperature
        tvCurrentDayConditionName = bindingFragment.tvCurrentDayConditionName
        tvRainPercent = bindingFragment.tvRainPercent
        tvCurrentWindSpeed = bindingFragment.tvCurrentWindSpeed
        tvCurrentWindDirection = bindingFragment.tvCurrentWindDirection
        tvCurrentTime = bindingFragment.tvCurrentTime
        tvCurrentDate = bindingFragment.tvCurrentDate
        btnChangeFragmentFiveDayForecast = bindingFragment.btnChangeFragmentFiveDayForecast

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[ViewModelWeatherDetailFragment::class.java]

    }

    private var cityId: Int? = null

    private fun getCityIdFromBundle() {

        cityId = requireArguments().getInt("cityId", 0)

        sendCurrentConditionApi()

    }

    private fun sendCurrentConditionApi() {

        viewModel.sendCurrentConditionApi(requireActivity(), cityId!!)

        observeCurrentConditionApi()

    }

    private lateinit var lsModelCurrentCondition: ArrayList<ModelGetCurrentCondition>

    private fun observeCurrentConditionApi() {

        viewModel.lsModelGetCurrentConditionObserver.observe(requireActivity(), Observer {

            if (it.isEmpty())
                return@Observer

            lsModelCurrentCondition = ArrayList()
            lsModelCurrentCondition.addAll(it)

            setCurrentConditionData()

        })

    }

    private lateinit var currentTemperature: String
    private lateinit var currentTemperatureIcon: String
    private lateinit var currentWeatherTitle: String
    private lateinit var currentTime: String
    private lateinit var currentDate: String
    private lateinit var currentWindSpeed: String
    private lateinit var currentWindDirection: String
    private lateinit var currentRainPercent: String

    private fun setCurrentConditionData() {

        val tempModelGetCurrentCondition = lsModelCurrentCondition[0]

        currentTemperature =
            tempModelGetCurrentCondition.temperature.temperatureInCelsius.value.toString()
        currentTemperatureIcon = tempModelGetCurrentCondition.weatherIcon.toString()
        currentWeatherTitle = tempModelGetCurrentCondition.weatherText
        currentWindSpeed = tempModelGetCurrentCondition.wind.speed.speedValueMetric.value.toString()
        currentWindDirection = tempModelGetCurrentCondition.wind.direction.localized

        getCurrentDate()

        setCurrentDataWithView()

        changeFragmentToFiveDayForecast()

    }

    private fun getCurrentDate() {

        val todayDate = Calendar.getInstance()

        val currentYear = todayDate.get(Calendar.YEAR)
        val currentMonth = todayDate.get(Calendar.MONTH)
        val currentDay = todayDate.get(Calendar.DAY_OF_MONTH)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm", Locale.ENGLISH)
        val currentCompleteDate = sdf.format(Date())

        val indexOfSpace = currentCompleteDate.indexOf(" ", 0)

        currentTime = currentCompleteDate.substring(indexOfSpace, currentCompleteDate.length)
        currentDate = "$currentYear/$currentMonth/$currentDay"

    }

    private fun setCurrentDataWithView() {

        tvCurrentTimeTemperature.text = currentTemperature
        tvCurrentDayConditionName.text = currentWeatherTitle
        tvCurrentWindDirection.text = currentWindDirection
        tvCurrentWindSpeed.text = currentWindSpeed
        imgCurrentTemperatureIcon.setBackgroundResource(
            resources.getIdentifier(
                "s_$currentTemperatureIcon",
                "drawable",
                requireActivity().packageName
            )
        )
        tvCurrentTime.text = currentTime
        tvCurrentDate.text = currentDate

    }

    private fun changeFragmentToFiveDayForecast() {

        btnChangeFragmentFiveDayForecast.setOnClickListener {

            val bundle = Bundle()
            bundle.putInt("cityId", cityId!!)

            Navigation.findNavController(it)
                .navigate(
                    R.id.action_weatherDetailFragment_to_fiveDayForecastFragment,
                    bundle
                )

        }

    }

}