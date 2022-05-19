package ir.arinateam.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.RadarFragmentBinding
import ir.arinateam.weather.utils.LoadingAnimation
import ir.arinateam.weather.viewmodel.ViewModelRadarFragment

class RadarFragment : Fragment() {

    private lateinit var bindingFragment: RadarFragmentBinding

    private lateinit var viewModel: ViewModelRadarFragment

    private lateinit var webViewRadar: WebView
    private lateinit var btnRadarRain: Button
    private lateinit var btnRadarTemp: Button
    private lateinit var btnRadarSnow: Button
    private lateinit var btnRadarWind: Button
    private lateinit var btnRadarPressure: Button
    private lateinit var btnRadarHumidity: Button
    private lateinit var btnRadarClouds: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.radar_fragment, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        setWebViewAddress()

        webViewRadar.loadUrl(radarWebViewTempAddress)

        setTempOverlay()

        setRainOverlay()

        setSnowOverlay()

        setWindOverlay()

        setPressureOverlay()

        setHumidityOverlay()

        setCloudsOverlay()

    }

    private fun initView() {

        webViewRadar = bindingFragment.webViewRadar
        btnRadarRain = bindingFragment.btnRadarRain
        btnRadarTemp = bindingFragment.btnRadarTemp
        btnRadarSnow = bindingFragment.btnRadarSnow
        btnRadarWind = bindingFragment.btnRadarWind
        btnRadarPressure = bindingFragment.btnRadarPressure
        btnRadarHumidity = bindingFragment.btnRadarHumidity
        btnRadarClouds = bindingFragment.btnRadarClouds

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[ViewModelRadarFragment::class.java]

    }

    private lateinit var loading: LoadingAnimation

    private val radarWebViewTempAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=temp&zoom=5"

    private fun setWebViewAddress() {

        webViewRadar.settings.javaScriptEnabled = true

        loading = LoadingAnimation(requireActivity())


        webViewRadar.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading.hideDialog()
            }

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

    }

    private fun setTempOverlay() {

        btnRadarTemp.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarTemp)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewTempAddress)

        }

    }

    private val radarWebViewRainAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=rain&zoom=5"

    private fun setRainOverlay() {

        btnRadarRain.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarRain)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewRainAddress)

        }

    }

    private val radarWebViewSnowAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=snow&zoom=5"

    private fun setSnowOverlay() {

        btnRadarSnow.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarSnow)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewSnowAddress)

        }

    }

    private val radarWebViewWindAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=wind&zoom=5"

    private fun setWindOverlay() {

        btnRadarWind.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarWind)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewWindAddress)

        }

    }

    private val radarWebViewPressureAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=pressure&zoom=5"

    private fun setPressureOverlay() {

        btnRadarPressure.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarPressure)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewPressureAddress)

        }

    }

    private val radarWebViewHumidityAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=humidity&zoom=5"

    private fun setHumidityOverlay() {

        btnRadarHumidity.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarHumidity)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewHumidityAddress)

        }

    }

    private val radarWebViewCloudsAddress =
        "https://goweatherradar.com/en/widget/17582421f5b9a27af7ad841425bfa0a93f093182?lat=35.677&lng=51.3445&metricRain=mm&metricTemp=c&overlay=clouds&zoom=5"

    private fun setCloudsOverlay() {

        btnRadarClouds.setOnClickListener {

            changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(btnRadarClouds)

            setWebViewAddress()

            webViewRadar.loadUrl(radarWebViewCloudsAddress)

        }

    }

    private fun changeSelectedButtonBackgroundToSelectedAndOtherButtonsToUnselected(selectedButton: Button) {

        btnRadarTemp.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarRain.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarSnow.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarWind.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarPressure.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarHumidity.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)
        btnRadarClouds.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_unselected)

        selectedButton.setBackgroundResource(R.drawable.bg_btn_radar_layer_type_selected)


    }

}