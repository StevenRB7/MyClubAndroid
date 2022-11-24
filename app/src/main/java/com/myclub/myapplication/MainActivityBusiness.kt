package com.myclub.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

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
import com.myclub.myapplication.Actvity.IniciarSesion
import com.myclub.myapplication.databinding.ActivityMainBusinessBinding
import com.myclub.myapplication.utils.dataStore.MySharedPreferences

class MainActivityBusiness : AppCompatActivity() {

    private lateinit var appBarConfiguration2: AppBarConfiguration
    private lateinit var binding: ActivityMainBusinessBinding
    private lateinit var navController2: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMainBusiness.toolbarBusiness)
        setBottom()


////////

///////
        val drawerLayout2: DrawerLayout = binding.drawerLayout2
        val navView2: NavigationView = binding.navViewBusiness

        val navController = findNavController(R.id.nav_host_fragment_content_mainbusiness)

        appBarConfiguration2 = AppBarConfiguration(
            setOf(
                R.id.nav_homeBusiness,
                R.id.nav_promocionesBusiness,
                R.id.nav_membresiaBusiness,
                R.id.nav_MetodosBusiness,
                R.id.nav_VauchersBusiness,
                R.id.nav_RedimirBusiness,
                R.id.nav_CodigoBusiness,
                R.id.nav_cerrarsesionBusiness,

                ///NAV BUTTON
                R.id.nav_perfilBusiness,
                R.id.nav_configuracionBusiness,
                R.id.nav_superpromosBusiness
            ), drawerLayout2
        )
        setupActionBarWithNavController(navController, appBarConfiguration2)
        navView2.setupWithNavController(navController)
        observeDestination2()

    }
    private fun setBottom() {
        val navHostFragment2 =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_mainbusiness) as NavHostFragment
        navController2 = navHostFragment2.navController

        val bottom = findViewById<BottomNavigationView>(R.id.nav_viewbuttonbusiness)
        bottom.setupWithNavController(navController2)
    }

    private fun observeDestination2() {
        navController2.addOnDestinationChangedListener { n, d, a ->
            when (d.id) {
                R.id.nav_homeBusiness -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbuttonbusiness)).visibility =
                        View.VISIBLE
                }
                R.id.nav_perfilBusiness -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbuttonbusiness)).visibility =
                        View.VISIBLE
                }
                R.id.nav_superpromosBusiness -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbuttonbusiness)).visibility =
                        View.VISIBLE
                }
                R.id.nav_configuracionBusiness -> {
                    (findViewById<BottomNavigationView>(R.id.nav_viewbuttonbusiness)).visibility =
                        View.VISIBLE
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cerrarsesion -> {
                MySharedPreferences(this).deleteMySharedPreferences()
                val i = Intent(this, IniciarSesion::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_mainbusiness)
        return navController.navigateUp(appBarConfiguration2) || super.onSupportNavigateUp()
    }


}