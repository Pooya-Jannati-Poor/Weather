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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.adapter.AdapterRecTwelveHoursForecast
import ir.arinateam.weather.databinding.WeatherDetailFragmentBinding
import ir.arinateam.weather.model.ModelGetCurrentCondition
import ir.arinateam.weather.model.ModelGetTwelveHoursForecast
import ir.arinateam.weather.viewmodel.ViewModelWeatherDetailFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class WeatherDetailFragment : Fragment() {

    private lateinit var bindingFragment: WeatherDetailFragmentBinding

    private lateinit var imgBackToSearchCity: ImageView
    private lateinit var tvMinMaxTemperature: TextView
    private lateinit var imgCurrentTemperatureIcon: ImageView
    private lateinit var tvCurrentTimeTemperature: TextView
    private lateinit var tvCurrentDayConditionName: TextView
    private lateinit var tvRainPercent: TextView
    private lateinit var tvCurrentWindSpeed: TextView
    private lateinit var tvCurrentWindDirection: TextView
    private lateinit var imgWindDirection: ImageView
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvCurrentDate: TextView
    private lateinit var btnChangeFragmentFiveDayForecast: Button
    private lateinit var btnChangeFragmentRadar: Button
    private lateinit var recTwelveHoursForecast: RecyclerView

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

        changeFragmentToCityFinder()

        getCityIdFromBundle()

    }

    private fun initView() {

        imgBackToSearchCity = bindingFragment.imgBackToSearchCity
        tvMinMaxTemperature = bindingFragment.tvMinMaxTemperature
        imgCurrentTemperatureIcon = bindingFragment.imgCurrentTemperatureIcon
        tvCurrentTimeTemperature = bindingFragment.tvCurrentTimeTemperature
        tvCurrentDayConditionName = bindingFragment.tvCurrentDayConditionName
        tvRainPercent = bindingFragment.tvRainPercent
        tvCurrentWindSpeed = bindingFragment.tvCurrentWindSpeed
        tvCurrentWindDirection = bindingFragment.tvCurrentWindDirection
        imgWindDirection = bindingFragment.imgWindDirection
        tvCurrentTime = bindingFragment.tvCurrentTime
        tvCurrentDate = bindingFragment.tvCurrentDate
        btnChangeFragmentFiveDayForecast = bindingFragment.btnChangeFragmentFiveDayForecast
        btnChangeFragmentRadar = bindingFragment.btnChangeFragmentRadar
        recTwelveHoursForecast = bindingFragment.recTwelveHoursForecast

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[ViewModelWeatherDetailFragment::class.java]

    }

    private fun changeFragmentToCityFinder() {

        imgBackToSearchCity.setOnClickListener {

            clearCityIdFromSharedPreference()

            findNavController().navigate(R.id.action_weatherDetailFragment_to_cityFinderFragment)

        }

    }

    private fun clearCityIdFromSharedPreference() {

        viewModel.clearCityIdFromSharedPreference(requireActivity())

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

            sendOneDayForecastApi()

        })

    }

    private lateinit var currentDayMinMaxTemperature: String
    private lateinit var currentTemperature: String
    private lateinit var currentTemperatureIcon: String
    private lateinit var currentWeatherTitle: String
    private lateinit var currentTime: String
    private lateinit var currentDate: String
    private lateinit var currentWindSpeed: String
    private lateinit var currentWindDirection: String
    private lateinit var currentWindDirectionDegree: String
    private lateinit var currentRainPercent: String

    private fun setCurrentConditionData() {

        val tempModelGetCurrentCondition = lsModelCurrentCondition[0]

        currentTemperature =
            tempModelGetCurrentCondition.temperature.temperatureInCelsius.value.toString()
        currentTemperatureIcon = tempModelGetCurrentCondition.weatherIcon.toString()
        currentWeatherTitle = tempModelGetCurrentCondition.weatherText
        currentWindSpeed = tempModelGetCurrentCondition.wind.speed.speedValueMetric.value.toString()
        currentWindDirection = tempModelGetCurrentCondition.wind.direction.localized
        currentWindDirectionDegree = tempModelGetCurrentCondition.wind.direction.degrees.toString()

        getCurrentDate()

        changeFragmentToFiveDayForecast()

        changeFragmentToRadar()

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

    private fun sendOneDayForecastApi() {

        viewModel.sendOneDayForecastApi(requireActivity(), cityId!!)

        observeOneDayForecastApi()

    }

    private fun observeOneDayForecastApi() {

        viewModel.lsModelGetOneDayForecastObserver.observe(requireActivity(), Observer {

            currentDayMinMaxTemperature =
                "${it.dailyForecasts[0].temperature.minimum.value}˚/${it.dailyForecasts[0].temperature.maximum.value}˚"
            currentRainPercent = it.dailyForecasts[0].day.precipitationProbability.toString()

            setCurrentDataWithView()

            sendTwelveHoursForecastApi()

        })

    }

    private fun setCurrentDataWithView() {

        tvCurrentTimeTemperature.text = currentTemperature
        tvCurrentDayConditionName.text = currentWeatherTitle
        tvCurrentWindDirection.text = currentWindDirection
        imgWindDirection.rotation = currentWindDirectionDegree.toFloat()
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
        tvRainPercent.text = currentRainPercent
        tvMinMaxTemperature.text = currentDayMinMaxTemperature

    }

    private lateinit var lsModelGetTwelveHoursForecast: ArrayList<ModelGetTwelveHoursForecast>

    private fun sendTwelveHoursForecastApi() {

        viewModel.sendTwelveHoursForecastApi(requireActivity(), cityId!!)

        viewModel.lsModelGetTwelveHoursForecast.observe(requireActivity(), Observer {

            lsModelGetTwelveHoursForecast = ArrayList()

            lsModelGetTwelveHoursForecast.addAll(it)

            setRecTwelveHours()

        })

    }

    private lateinit var adapterRecTwelveHoursForecast: AdapterRecTwelveHoursForecast

    private fun setRecTwelveHours() {

        adapterRecTwelveHoursForecast =
            AdapterRecTwelveHoursForecast(requireActivity(), lsModelGetTwelveHoursForecast)

        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        recTwelveHoursForecast.layoutManager = linearLayoutManager
        recTwelveHoursForecast.adapter = adapterRecTwelveHoursForecast

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

    private fun changeFragmentToRadar() {

        btnChangeFragmentRadar.setOnClickListener {

            findNavController().navigate(R.id.action_weatherDetailFragment_to_radarFragment)

        }

    }

}