package com.capstone.peradaban.singkat.beranda.hubungi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.databinding.ActivityHubungiBinding

class HubungiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHubungiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHubungiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnCall: Button = binding.btnCall
        btnCall.setOnClickListener {
            val phoneNumber = "0213290785"
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialPhoneIntent)
        }

        val btnEmail: Button = binding.btnEmail
        btnEmail.setOnClickListener {
            val email = "singkat@jelas.go.id"
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            startActivity(emailIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return  super.onSupportNavigateUp()
    }
}