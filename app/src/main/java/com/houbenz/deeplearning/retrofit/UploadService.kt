package com.houbenz.deeplearning.retrofit

import com.houbenz.deeplearning.ui.predict.PredictFragment
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadService {

    @Multipart
    @POST("predict")
    fun upload(@Part photo: MultipartBody.Part): Call<PredictFragment.Prediction>


    /** */
    @Headers("Accept: application/json")
    @POST("logout")
    fun logout(@Header("Authorization") token: String?): Call<Message?>?


    @Headers("Accept: application/json")
    @POST("checkToken")
    fun checkLogin(@Header("Authorization") token: String?): Call<Message?>?


    @Headers("Accept: application/json", "API_KEY: a2dc14c5-7198-4f41-8c03-0041f07a882a")
    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<ResultUser?>?


    @Headers("Accept: application/json", "API_KEY: a2dc14c5-7198-4f41-8c03-0041f07a882a")
    @POST("register")
    fun registerUser(@Body user: User?): Call<ResultUser?>?


    //String BASE_URL="http://www.winyourcar.fr/api/";
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("password/email")
    fun sendEmailResetPassword(@Field("email") email: String?): Call<Message?>?
    /******************************************************/
}

class Message(var message: String)

class User(
    var name: String, var email: String,
    var password: String, var birthday: String,
    var gender: String,
    var password_confirmation: String
){
}
class ResultUser(var user: User, var token: String, var message: String)



//Retrofit singleton, note that i only return the service
object RetrofitFactory{
}
