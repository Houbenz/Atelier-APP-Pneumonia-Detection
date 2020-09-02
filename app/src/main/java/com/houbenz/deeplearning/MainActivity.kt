package com.houbenz.deeplearning

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.houbenz.deeplearning.retrofit.Message
import com.houbenz.deeplearning.retrofit.Singleton
import com.houbenz.deeplearning.retrofit.URL
import com.houbenz.deeplearning.retrofit.UploadService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_results, R.id.navigation_predict))

        navView.setupWithNavController(navController)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId == R.id.action_settings){
            val intent = Intent(applicationContext,SettingsActivity::class.java)
            startActivity(intent)
            }

        if(item.itemId == R.id.action_settingzs){
            val builder= AlertDialog.Builder(this,R.style.Theme_MaterialComponents_Dialog_Alert)
            builder.setTitle("Voulez vous déconnectez ?")
                .setPositiveButton("Déconnecter") { dialogInterface, i ->
                    disconnectRequest()
                    dialogInterface.cancel()
                    dialogInterface.dismiss()

                }
                .setNegativeButton("Annuler") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    dialogInterface.cancel()
                }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }



    private fun disconnectRequest(){



        URL.api.laravel_api
        val uploadService=Singleton.retorfitLaravel.create(UploadService::class.java)

        val token= getSharedPreferences("user", Context.MODE_PRIVATE).getString("token","null")

        Log.i("okk","token logout "+ token)

        val callLogin: Call<Message?>?=  uploadService.logout(token)

        callLogin?.enqueue(object : Callback<Message?>{

            override fun onResponse(call: Call<Message?>, response: Response<Message?>) {

                Log.i("okk","response code ${response.code()}")

                if (response.code() == 200){
                    Toast.makeText(applicationContext,"Vous êtes déconnecté",Toast.LENGTH_LONG).show()

                    finish()
                    Log.i("okk",response.body()!!.message)
                }else{
                    Toast.makeText(applicationContext,"Vérifier votre connectivité",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Message?>, t: Throwable) {

            }
        })


    }
}
