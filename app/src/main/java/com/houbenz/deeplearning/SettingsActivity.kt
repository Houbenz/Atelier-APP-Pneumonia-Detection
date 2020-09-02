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

class SettingsActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {

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
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val ipAddressEditText=findPreference<EditTextPreference>("ip_address")
           
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

        Log.i("okk","before laravel ${URL.api.laravel_api}, flask ${URL.api.flask_api}")

        URL.api.laravel_api= PreferenceManager.getDefaultSharedPreferences(this).getString("ip_address_laravel","null").toString()
        URL.api.flask_api= PreferenceManager.getDefaultSharedPreferences(this).getString("ip_address_flask","null").toString()

        Log.i("okk","after laravel ${URL.api.laravel_api}, flask ${URL.api.flask_api}")
    }
}