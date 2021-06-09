package com.capstone.peradaban.singkat.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.ajukan.PhoneAuthActivity
import com.capstone.peradaban.singkat.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_beranda, R.id.navigation_status))

        navView.background = null
        navView.menu.getItem(1).isEnabled = false

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val fabDaftar: FloatingActionButton = binding.fabDaftar
        fabDaftar.setOnClickListener {
            val intent = Intent(this@MainActivity, PhoneAuthActivity::class.java)
            startActivity(intent)
        }
    }
}