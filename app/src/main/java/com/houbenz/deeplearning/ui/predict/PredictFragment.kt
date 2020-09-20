package com.houbenz.deeplearning.ui.predict

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.houbenz.deeplearning.R
import com.houbenz.deeplearning.SettingsActivity
import com.houbenz.deeplearning.retrofit.Singleton
import com.houbenz.deeplearning.retrofit.URL
import com.houbenz.deeplearning.retrofit.UploadService
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class PredictFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var upload: Button
    private lateinit var ok_button: ImageView
    private lateinit var close_button: ImageView
    private lateinit var buttons_layout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_predict, container, false)
        imageView=root.findViewById(R.id.image_predict)
        textView=root.findViewById(R.id.textview_predict)
        upload=root.findViewById(R.id.uploadButton)
        ok_button=root.findViewById(R.id.ok_button)
        close_button=root.findViewById(R.id.close_button)
        buttons_layout=root.findViewById(R.id.buttons_layout)
        upload.setOnClickListener {
            val intent  =Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,15)
         }

        ok_button.setOnClickListener {
            handlerImageUpload()
        }

        close_button.setOnClickListener {

            if (imageView.drawable != null){
                imageView.setImageDrawable(null)
                buttons_layout.visibility=View.GONE
            }
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 15){
                imageView.setImageURI(data?.data)
                textView.visibility=View.GONE
                buttons_layout.visibility=View.VISIBLE
            }
        }
    }

    private fun imageToFile():ByteArray{

        val bitmap= imageView.drawable.toBitmap()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos)
        val bytes=bos.toByteArray()
        return bytes
    }


    private fun handlerImageUpload(){


        var dialog=ProgressDialog(activity)

        dialog.show()


        val file = imageToFile()
        val fileRequestBody=RequestBody.create(MediaType.parse("image/*"),file)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("image","image.png",fileRequestBody)

        Log.i("okk","flask ${URL.api.flask_api}")



        try {
                 var retorfit=
                    Retrofit.Builder()
                        .baseUrl(URL.api.flask_api)
                        .client(OkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                        .build()


            val uploadService=retorfit.create(UploadService::class.java)
            val uploadCall: Call<Prediction> = uploadService.upload(image)


            uploadCall.enqueue(object : Callback<Prediction>{
                override fun onResponse(call: Call<Prediction>, response: Response<Prediction>) {

                    Log.i("okk","code response " +response.code())
                    if (response.code() == 200){

                        if (response.body() != null){

                            dialog.dismiss()
                            val resultDialog =makeDialog(response.body()!!)
                            resultDialog.show()
                            Toast.makeText(context,"Image uploaded succesfully : "+ response.body()?.prediction,Toast.LENGTH_LONG).show()
                            Log.i("okk",response.body().toString())
                        }

                    }else
                    {
                        Log.i("okk","response error body : "+response.errorBody())
                        dialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<Prediction>, t: Throwable) {
                    Log.i("okk","response error body : "+t.message)
                    call.cancel()
                    dialog.dismiss()
                }
            })

        }catch (e:Exception){
            Toast.makeText(context,"link not valid",Toast.LENGTH_LONG).show()
        }

    }

    private fun makeDialog(prediction: Prediction):Dialog{

        val builder = AlertDialog.Builder(context)
        val view: View= requireActivity().layoutInflater.inflate(R.layout.dialog_prediction,null)

        val closeDialog:Button=view.findViewById(R.id.closeDialog)


        val predictionText:TextView=view.findViewById(R.id.predictionText)
        val percentageText:TextView=view.findViewById(R.id.percentageText)

        if (prediction.prediction.equals("Normal",true))
            predictionText.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
        else
            predictionText.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))

        predictionText.setText(prediction.prediction)

        percentageText.setText(String.format(Locale.US,"%.2f", prediction.percentage))

        val dialog=builder.setView(view).create()

        closeDialog.setOnClickListener {
            dialog.cancel()
            dialog.dismiss()
        }

        return dialog
    }




    class Prediction(var percentage:Float, var prediction:String, var success:Boolean){

        override fun toString(): String {
            return "Prediciton(percentage=$percentage, prediction='$prediction', succes='$success')"
        }

    }
}