package ir.arinateam.weather.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ir.arinateam.weather.R
import ir.arinateam.weather.databinding.LayoutRecSearchCityBinding
import ir.arinateam.weather.model.ModelRecCityName

class AdapterRecSearchCity(
    private val context: Context,
    private val lsModelRecCityName: ArrayList<ModelRecCityName>
) : RecyclerView.Adapter<AdapterRecSearchCity.ItemAdapter>() {

    private lateinit var bindingAdapter: LayoutRecSearchCityBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter {
        val inflater = LayoutInflater.from(context)
        bindingAdapter =
            DataBindingUtil.inflate(inflater, R.layout.layout_rec_search_city, parent, false)
        return ItemAdapter(bindingAdapter)
    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {

        val tempModel = lsModelRecCityName[position]

        holder.tvCityName.text = tempModel.name
        holder.tvCountryName.text = tempModel.country.countryName
        holder.tvProvinceName.text = tempModel.province.provinceName

        holder.itemView.setOnClickListener {

            changeFragmentToWeatherDetail(it, tempModel.id)

        }

    }

    private fun changeFragmentToWeatherDetail(view: View, cityId: Int) {

        val bundle = Bundle()
        bundle.putInt("cityId", cityId)

        Navigation.findNavController(view)
            .navigate(R.id.action_cityFinderFragment_to_weatherDetailFragment, bundle)

    }

    override fun getItemCount(): Int = lsModelRecCityName.size

    inner class ItemAdapter(binding: LayoutRecSearchCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvCityName: TextView = binding.tvCityName
        val tvProvinceName: TextView = binding.tvProvinceName
        val tvCountryName: TextView = binding.tvCountryName

    }

}