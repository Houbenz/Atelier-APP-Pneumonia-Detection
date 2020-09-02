package com.houbenz.deeplearning

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout

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
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                    finish()
                }
                .setNegativeButton("Annuler") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    dialogInterface.cancel()
                }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}