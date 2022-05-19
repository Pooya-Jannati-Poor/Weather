package ir.arinateam.weather.repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class CityIdManagerRepository(private val context: Context) {

    private lateinit var encryptedSharedPreferences: SharedPreferences
    private lateinit var masterKeys: String

    fun saveCityIdInSharedPreference(cityId: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                "Data",
                masterKeys,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

        } else {

            encryptedSharedPreferences = context.getSharedPreferences(
                "Data",
                Context.MODE_PRIVATE
            )

        }


        val edSharedPreferences = encryptedSharedPreferences.edit()
        edSharedPreferences.putInt("cityId", cityId)
        edSharedPreferences.apply()

    }

    fun readAndReturnCityIdFromSharedPreference(): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                "Data",
                masterKeys,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

        } else {

            encryptedSharedPreferences = context.getSharedPreferences(
                "Data",
                Context.MODE_PRIVATE
            )

        }

        return encryptedSharedPreferences.getInt("cityId", 0)

    }

    fun clearCityIdFromSharedPreference() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                "Data",
                masterKeys,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

        } else {

            encryptedSharedPreferences = context.getSharedPreferences(
                "Data",
                Context.MODE_PRIVATE
            )

        }

        val edSharedPreferences = encryptedSharedPreferences.edit()
        edSharedPreferences.clear()
        edSharedPreferences.apply()

    }

}