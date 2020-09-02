package com.houbenz.deeplearning.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Singleton{

    companion object{
        @JvmField
        val retorfit=
            Retrofit.Builder()
                .baseUrl("http://dabc13b2df15.ngrok.io/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()
    }
}