package ir.arinateam.weather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.adapter.AdapterRecSearchCity
import ir.arinateam.weather.databinding.CityFinderFragmentBinding
import ir.arinateam.weather.model.ModelRecCityName
import ir.arinateam.weather.viewmodel.ViewModelCityFinderFragment

class CityFinderFragment : Fragment() {

    private lateinit var bindingFragment: CityFinderFragmentBinding

    private lateinit var viewModel: ViewModelCityFinderFragment

    private lateinit var edCityName: EditText
    private lateinit var btnSearch: Button
    private lateinit var recCityName: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.city_finder_fragment, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        checkSharedPreferenceForCityId()

        clickBtnSearch()

    }

    private fun initView() {

        edCityName = bindingFragment.edCityName
        btnSearch = bindingFragment.btnSearch
        recCityName = bindingFragment.recCityName

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[ViewModelCityFinderFragment::class.java]

    }

    private fun checkSharedPreferenceForCityId() {

        viewModel.readCityIdFromSharedPreference(requireActivity())

        observeCityId()

    }

    private var cityId: Int? = null

    private fun observeCityId() {

        viewModel.cityId.observe(requireActivity(), Observer {

            if (it == 0)
                return@Observer

            cityId = it

            changeFragmentToWeatherDetailFragment()

        })

    }

    private fun changeFragmentToWeatherDetailFragment() {

        val bundle = Bundle()
        bundle.putInt("cityId", cityId!!)

        findNavController().navigate(
            R.id.action_cityFinderFragment_to_weatherDetailFragment,
            bundle
        )

    }

    private fun validateCityNameInput() {

        getCityNameFromInput()

        if (cityName.isEmpty())
            return

        sendSearchApi()

    }

    private lateinit var cityName: String

    private fun getCityNameFromInput() {

        cityName = edCityName.text.toString().trim()

    }

    private fun clickBtnSearch() {

        btnSearch.setOnClickListener {

            validateCityNameInput()

        }

    }

    private fun sendSearchApi() {

        viewModel.sendCitySearchApi(requireActivity(), cityName)

        observeCitySearchApi()

    }

    private lateinit var lsModelRecCityName: ArrayList<ModelRecCityName>
    private lateinit var adapterRecCityName: AdapterRecSearchCity

    private fun observeCitySearchApi() {

        viewModel.citySearchApiResult.observe(requireActivity(), Observer {

            if (!it)
                return@Observer

            observeLsModelRecCityName()

            Toast.makeText(requireContext(), "request success", Toast.LENGTH_SHORT).show()

        })

    }

    private fun observeLsModelRecCityName() {

        viewModel.lsModelRecCityNameObserver.observe(requireActivity(), Observer {

            lsModelRecCityName = ArrayList()
            lsModelRecCityName.addAll(it)

            setRecSearchCity()

        })

    }

    private fun setRecSearchCity() {

        adapterRecCityName = AdapterRecSearchCity(requireActivity(), lsModelRecCityName, viewModel)

        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recCityName.layoutManager = linearLayoutManager
        recCityName.adapter = adapterRecCityName

    }

}