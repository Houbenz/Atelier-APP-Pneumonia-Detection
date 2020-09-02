package com.houbenz.deeplearning.retrofit

import com.houbenz.deeplearning.ui.predict.PredictFragment
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadService {

    @Multipart
    @POST("predict")
    fun upload(@Part photo: MultipartBody.Part): Call<PredictFragment.Prediction>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun loginUser(@Field("email") email: String?, @Field("password") password: String? ): Call<ResultUser?>?

    @Headers("Accept: application/json")
    @POST("register")
    fun registerUser(@Body user: User?): Call<ResultUser?>?

    /** */
    @Headers("Accept: application/json")
    @GET("logout")
    fun logout(@Header("Authorization") token: String?): Call<Message?>?


    @Headers("Accept: application/json")
    @GET("checktoken")
    fun checkLogin(@Header("Authorization") token: String?): Call<Message?>?

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("password/email")
    fun sendEmailResetPassword(@Field("email") email: String?): Call<Message?>?
    /******************************************************/
}

class Message(var message: String)

class User(
    var name: String,
    var email: String,
    var password: String
){
}
class ResultUser(var user: User, var token: String, var message: String)



//Retrofit singleton, note that i only return the service
object RetrofitFactory{
}

class URL{
    object api{
        var flask_api="http://69a48a8830cb.ngrok.io/"
        var laravel_api="http://192.168.1.24:8000/api/"
    }
}