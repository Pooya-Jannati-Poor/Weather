package ir.arinateam.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.adapter.AdapterRecFiveDayForecast
import ir.arinateam.weather.databinding.FiveDayForecastFragmentBinding
import ir.arinateam.weather.model.ModelDailyForecast
import ir.arinateam.weather.viewmodel.ViewModelFiveDayForecastFragment
import java.util.ArrayList

class FiveDayForecastFragment : Fragment() {

    private lateinit var bindingFragment: FiveDayForecastFragmentBinding

    private lateinit var viewModel: ViewModelFiveDayForecastFragment

    private lateinit var recFiveDayForecast: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.five_day_forecast_fragment, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        getCityIdFromBundle()

    }

    private fun initView() {

        recFiveDayForecast = bindingFragment.recFiveDayForecast

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[ViewModelFiveDayForecastFragment::class.java]

    }

    private var cityId: Int? = null

    private fun getCityIdFromBundle() {

        cityId = requireArguments().getInt("cityId", 0)

        sendFiveDayForecastApi()

    }

    private fun sendFiveDayForecastApi() {

        viewModel.sendFiveDayForecastApi(requireActivity(), cityId!!)

        observeCurrentConditionApi()

    }

    private lateinit var lsModelDailyForecast: ArrayList<ModelDailyForecast>

    private fun observeCurrentConditionApi() {

        viewModel.lsModelGetFiveDayForecastObserver.observe(requireActivity(), Observer {

            if (it.dailyForecasts.isEmpty())
                return@Observer

            lsModelDailyForecast = ArrayList()
            lsModelDailyForecast.addAll(it.dailyForecasts)

            setRecFiveDayForecast()

        })

    }

    private lateinit var adapterRecFiveDayForecast: AdapterRecFiveDayForecast

    private fun setRecFiveDayForecast() {

        adapterRecFiveDayForecast =
            AdapterRecFiveDayForecast(requireActivity(), lsModelDailyForecast)

        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        recFiveDayForecast.layoutManager = linearLayoutManager
        recFiveDayForecast.adapter = adapterRecFiveDayForecast

    }

}