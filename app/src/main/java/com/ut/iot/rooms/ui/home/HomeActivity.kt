package com.ut.iot.rooms.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ut.iot.rooms.R
import com.ut.iot.rooms.data.model.ResourceLoading
import com.ut.iot.rooms.ui.BaseActivity
import com.ut.iot.rooms.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber


class HomeActivity : BaseActivity() {


    override fun handleResourceLoading(resourceLoading: ResourceLoading?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        if(intent.hasExtra("message")) {
            Timber.d("Message ${intent.getIntExtra("message", 0)}")
            showSuccess(getString(intent.getIntExtra("message", 0)))
            intent.removeExtra("message");
        }
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                toAuthActivity()
            }
            R.id.action_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        }
        return true
    }
}
