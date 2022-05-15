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
import ir.arinateam.weather.model.ModelGetFutureDayForecast
import ir.arinateam.weather.utils.LoadingAnimation

class ViewModelFiveDayForecastFragment(application: Application): AndroidViewModel(application) {

    private lateinit var loading: LoadingAnimation

    private lateinit var apiClient: ApiClient

    private val fiveDayForecastApiDisposable: CompositeDisposable = CompositeDisposable()
    val lsModelGetFiveDayForecastObserver: MutableLiveData<ModelGetFutureDayForecast> =
        MutableLiveData()

    fun sendFiveDayForecastApi(context: Context, cityId: Int) {

        loading = LoadingAnimation(context)

        apiClient = ApiClient()

        fiveDayForecastApiDisposable.add(
            apiClient.getFiveDayForecast(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<ModelGetFutureDayForecast>() {
                    override fun onSuccess(t: ModelGetFutureDayForecast) {

                        loading.hideDialog()

                        lsModelGetFiveDayForecastObserver.postValue(t)

                        clearDisposable()

                    }

                    override fun onError(e: Throwable) {

                        loading.hideDialog()

                        e.printStackTrace()

                        clearDisposable()

                    }

                })

        )

    }

    private fun clearDisposable() {
        fiveDayForecastApiDisposable.clear()
    }

    override fun onCleared() {
        clearDisposable()
        super.onCleared()
    }

}