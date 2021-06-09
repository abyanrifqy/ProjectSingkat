package com.capstone.peradaban.singkat.ajukan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.capstone.peradaban.singkat.databinding.ActivityKetentuanBinding
import com.google.firebase.auth.FirebaseAuth

class KetentuanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKetentuanBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKetentuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser==null){
//            startActivity(Intent(this@KetentuanActivity, PhoneAuthActivity::class.java))
//            finish()
        }
        val btnKetentuan : Button = binding.btnKetentuan
        btnKetentuan.setOnClickListener {
            startActivity(Intent(this,PendaftaranActivity::class.java))
            finish()
        }
    }
}