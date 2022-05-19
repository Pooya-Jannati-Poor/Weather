package ir.arinateam.weather.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.arinateam.weather.api.ApiClient
import ir.arinateam.weather.model.ModelRecCityName
import ir.arinateam.weather.repository.CityIdManagerRepository
import ir.arinateam.weather.utils.LoadingAnimation

class ViewModelCityFinderFragment(application: Application) : AndroidViewModel(application) {

    private lateinit var loading: LoadingAnimation

    private lateinit var apiClient: ApiClient
    private val citySearchApiDisposable: CompositeDisposable = CompositeDisposable()
    val citySearchApiResult: MutableLiveData<Boolean> = MutableLiveData()
    val lsModelRecCityNameObserver: MutableLiveData<ArrayList<ModelRecCityName>> = MutableLiveData()

    fun sendCitySearchApi(context: Context, cityName: String) {

        apiClient = ApiClient()

        loading = LoadingAnimation(context)

        citySearchApiDisposable.add(
            apiClient.getCitiesName(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<ModelRecCityName>>() {
                    override fun onSuccess(t: ArrayList<ModelRecCityName>) {

                        loading.hideDialog()

                        lsModelRecCityNameObserver.postValue(t)

                        citySearchApiResult.postValue(true)

                    }

                    override fun onError(e: Throwable) {

                        loading.hideDialog()

                        e.printStackTrace()

                        citySearchApiResult.postValue(false)

                    }

                })

        )

    }

    private lateinit var cityIdManagerRepository: CityIdManagerRepository

    fun saveCityIdInSharedPreference(context: Context, cityId: Int) {

        cityIdManagerRepository = CityIdManagerRepository(context)

        cityIdManagerRepository.saveCityIdInSharedPreference(cityId)

    }

    val cityId: MutableLiveData<Int> = MutableLiveData()

    fun readCityIdFromSharedPreference(context: Context) {

        cityIdManagerRepository = CityIdManagerRepository(context)

        cityId.postValue(cityIdManagerRepository.readAndReturnCityIdFromSharedPreference())

    }

    override fun onCleared() {
        citySearchApiDisposable.clear()
        super.onCleared()
    }

}