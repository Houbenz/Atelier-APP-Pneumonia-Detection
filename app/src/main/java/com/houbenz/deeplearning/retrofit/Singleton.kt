package com.houbenz.deeplearning.retrofit

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Singleton{

    companion object{

        @JvmField
        var context: Context? =null


        @JvmField
        val retorfit=
            Retrofit.Builder()
                .baseUrl("")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
    }
}