package com.capstone.peradaban.singkat.status

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.databinding.ActivityDetailStatusBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailStatusActivity : AppCompatActivity() {
    companion object{
        const val KEY = "NIK_KEY"
    }
    private lateinit var binding: ActivityDetailStatusBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras

        if(extras != null) {
            val nik = extras.getString(KEY)
            Log.d("MAIN_ES", "$nik")
            if (nik != null) {
                readData(nik)
            }
        }
    }

    private fun readData(data: String){
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(data).get().addOnSuccessListener {
            if (it.exists()){
                val nik = it.child("nik").value
                val fullname= it.child("fullname").value
                val jenisPPks = it.child("jenisppks").value
                val alamat = it.child("alamatSekarang").value
                binding.tvNik.text = nik.toString()
                binding.tvNama.text = fullname.toString()
                binding.tvPPKS.text = jenisPPks.toString()
                binding.tvalamat.text = alamat.toString()


            }
        }.addOnFailureListener {
            Log.d("MAIN_ES","ERROR")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return  super.onSupportNavigateUp()
    }
}