package ir.arinateam.weather.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.arinateam.weather.api.ApiClient
import ir.arinateam.weather.model.ModelGetCurrentCondition
import ir.arinateam.weather.model.ModelGetFutureDayForecast
import ir.arinateam.weather.model.ModelGetTwelveHoursForecast
import ir.arinateam.weather.repository.CityIdManagerRepository
import ir.arinateam.weather.utils.LoadingAnimation

class ViewModelWeatherDetailFragment(application: Application) : AndroidViewModel(application) {

    private lateinit var cityIdManagerRepository: CityIdManagerRepository

    fun clearCityIdFromSharedPreference(context: Context) {

        cityIdManagerRepository = CityIdManagerRepository(context)

        cityIdManagerRepository.clearCityIdFromSharedPreference()

    }

    private lateinit var loading: LoadingAnimation

    private lateinit var apiClient: ApiClient
    private val currentConditionApiDisposable: CompositeDisposable = CompositeDisposable()
    val lsModelGetCurrentConditionObserver: MutableLiveData<ArrayList<ModelGetCurrentCondition>> =
        MutableLiveData()

    fun sendCurrentConditionApi(context: Context, cityId: Int) {

        apiClient = ApiClient()

        loading = LoadingAnimation(context)

        currentConditionApiDisposable.add(
            apiClient.getCurrentCondition(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<ArrayList<ModelGetCurrentCondition>>() {
                    override fun onSuccess(t: ArrayList<ModelGetCurrentCondition>) {

                        loading.hideDialog()

                        lsModelGetCurrentConditionObserver.postValue(t)

                    }

                    override fun onError(e: Throwable) {

                        loading.hideDialog()

                        e.printStackTrace()

                    }

                })

        )

    }

    private val oneDayForecastApiDisposable: CompositeDisposable = CompositeDisposable()
    val lsModelGetOneDayForecastObserver: MutableLiveData<ModelGetFutureDayForecast> =
        MutableLiveData()

    fun sendOneDayForecastApi(context: Context, cityId: Int) {

        loading = LoadingAnimation(context)

        apiClient = ApiClient()

        oneDayForecastApiDisposable.add(
            apiClient.getOneDayForecast(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<ModelGetFutureDayForecast>() {
                    override fun onSuccess(t: ModelGetFutureDayForecast) {

                        loading.hideDialog()

                        lsModelGetOneDayForecastObserver.postValue(t)

                    }

                    override fun onError(e: Throwable) {

                        loading.hideDialog()

                        e.printStackTrace()

                    }

                })

        )

    }

    private val twelveHoursForecastApiDisposable: CompositeDisposable = CompositeDisposable()
    val lsModelGetTwelveHoursForecast: MutableLiveData<ArrayList<ModelGetTwelveHoursForecast>> =
        MutableLiveData()

    fun sendTwelveHoursForecastApi(context: Context, cityId: Int) {

        loading = LoadingAnimation(context)

        apiClient = ApiClient()

        twelveHoursForecastApiDisposable.add(
            apiClient.getTwelveHoursForecast(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<ArrayList<ModelGetTwelveHoursForecast>>() {
                    override fun onSuccess(t: ArrayList<ModelGetTwelveHoursForecast>) {

                        loading.hideDialog()

                        lsModelGetTwelveHoursForecast.postValue(t)

                    }

                    override fun onError(e: Throwable) {

                        loading.hideDialog()

                        e.printStackTrace()

                    }

                })

        )

    }

    private fun clearDisposable() {
        oneDayForecastApiDisposable.clear()
        currentConditionApiDisposable.clear()
    }

    override fun onCleared() {
        clearDisposable()
        super.onCleared()
    }

}