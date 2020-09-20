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
    @JvmField
    var name: String,

    @JvmField
    var email: String,

    @JvmField
    var password: String,

    @JvmField
    var password_confirmation: String
){
}
class ResultUser(var user: User, var token: String, var message: String)



//Retrofit singleton, note that i only return the service
object RetrofitFactory{
}

class URL{
    object api{
        var flask_api=""
        var laravel_api=""
    }
}