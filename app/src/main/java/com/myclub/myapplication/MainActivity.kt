package com.myclub.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View

import android.widget.ImageView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myclub.myapplication.Actvity.Notificaciones
import com.myclub.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        setBottom()


////////
        binding.appBarMain.toolbar.setOnClickListener { view ->
            val  notificacion : ImageView = findViewById(R.id.idperfilmenu)
            notificacion.setOnClickListener {
                val i = Intent(this, Notificaciones::class.java)
                startActivity(i)
            }
        }
///////
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_promociones,
                R.id.nav_membresia,
                R.id.nav_Metodos,
                R.id.nav_Vauchers,
                R.id.nav_Redimir,
                R.id.nav_Codigo,

                ///NAV BUTTON
                R.id.nav_perfil,
                R.id.nav_configuracion,
                R.id.nav_superpromos
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        observeDestination()

    }
    private fun setBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        val bottom = findViewById<BottomNavigationView>(R.id.nav_viewbutton)
        bottom.setupWithNavController(navController)
    }

    private fun observeDestination() {
        navController.addOnDestinationChangedListener { n, d, a ->
            when (d.id) {
                R.id.nav_home -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbutton)).visibility =
                        View.VISIBLE
                }
                R.id.nav_perfil -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbutton)).visibility =
                        View.VISIBLE
                }
                R.id.nav_superpromos -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbutton)).visibility =
                        View.VISIBLE
                }
                R.id.nav_configuracion -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbutton)).visibility =
                        View.VISIBLE
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}