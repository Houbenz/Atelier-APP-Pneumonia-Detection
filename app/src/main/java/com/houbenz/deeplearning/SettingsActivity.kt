package com.houbenz.deeplearning

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.houbenz.deeplearning.retrofit.URL

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        val listener: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences, s: String ->

                Log.i("okk"," im called")

                Log.i("okk","before laravel ${URL.api.laravel_api}, flask ${URL.api.flask_api}")


                URL.api.laravel_api= sharedPreferences.getString("ip_address_laravel","null")
                    .toString()
                URL.api.flask_api= sharedPreferences.getString("ip_address_flask","null")
                    .toString()

                Log.i("okk","after laravel ${URL.api.laravel_api}, flask ${URL.api.flask_api}")
            }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val ipAddressEditText=findPreference<EditTextPreference>("ip_address_laravel")
            Log.i("okk","before laravel ${ipAddressEditText?.text}")

            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        }
    }

}