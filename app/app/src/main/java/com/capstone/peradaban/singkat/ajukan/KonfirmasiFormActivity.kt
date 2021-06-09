package com.capstone.peradaban.singkat.ajukan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.ui.MainActivity


class KonfirmasiFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_form)

        supportActionBar?.hide()

        Handler(mainLooper).postDelayed({
            val intent = Intent(this@KonfirmasiFormActivity, MainActivity::class.java)
            startActivity(intent)
            finish() }, 5000)
    }
}