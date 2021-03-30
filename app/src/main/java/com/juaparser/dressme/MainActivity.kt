package com.juaparser.dressme

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.get
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.juaparser.dressme.database.DressMeDatabase

/*
*
* Actividad principal de la aplicación, iniciada después de finalizar el tutorial.
*
* Se inicia el menú lateral así como el resto de componentes.
*
 */

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        this.navController = navController

        val infoButton : MaterialButton = findViewById(R.id.btn_info)
        infoButton.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

        infoButton.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_info)
            drawerLayout.closeDrawers()

        }

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_misConjuntos,
            R.id.nav_armario
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        if(navController.graph[R.id.nav_filtrarConjunto].id == navController.currentDestination?.id) {
            DressMeApp.listCheckboxColores = mutableListOf()
            DressMeApp.listCheckboxTiempo = mutableListOf()
            DressMeApp.listCheckboxPrendas = mutableListOf()
        }
        if(navController.graph.startDestination == navController.currentDestination?.id) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if(navController.graph[R.id.nav_filtrarConjunto].id == navController.currentDestination?.id) {
            DressMeApp.listCheckboxColores = mutableListOf()
            DressMeApp.listCheckboxTiempo = mutableListOf()
            DressMeApp.listCheckboxPrendas = mutableListOf()
        }
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}