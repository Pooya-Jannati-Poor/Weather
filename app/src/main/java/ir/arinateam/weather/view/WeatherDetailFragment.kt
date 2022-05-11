package ir.arinateam.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.WeatherDetailFragmentBinding

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

    }

}