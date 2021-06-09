package com.capstone.peradaban.admin_singkat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.capstone.peradaban.admin_singkat.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    companion object{
        const val KEY = "NIK_KEY"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var databse : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras

        if(extras != null){
            val nik = extras.getString(KEY)
            Log.d("MAIN_ES", "$nik")
            if (nik != null) {
                readDeata(nik)
                binding.btnUpdate.setOnClickListener {
                    val radioGroup: RadioGroup = binding.rbstatus
                    val selectJK: Int = radioGroup.checkedRadioButtonId
                    if (selectJK!= -1) {
                        val radio: RadioButton = findViewById(selectJK)
                        val status = radio.text.toString()
                        updateData(nik, status)

                    }
                }
            }
        }
    }

    fun readDeata(nik: String){
        databse = FirebaseDatabase.getInstance().getReference("Users")
        databse.child(nik).get().addOnSuccessListener {
            if (it.exists()){
//                val nomorNik = it.child("nik").value
                val nama = it.child("fullname").value
                val jenisppks = it.child("jenisppks").value
                val alamat = it.child("alamatSekarang").value
                val toilet = it.child("toilet").value
                val result = it.child("resultModel").value
                val bercerai = it.child("bercerai").value
                val disabilitas = it.child("disabilitas").value
                val jumlahanak12 = it.child("jumlahAnak12").value
                val jumlahanak19 = it.child("jumlahAnak19").value
                val jumlahAnggota = it.child("jumlahAnggotaKeluarga").value
                val jumlahLansia = it.child("jumlahLansia").value
                val jumlahTanggungan = it.child("jumlahTanggungan").value
                val komputer = it.child("komputer").value
                val lamaSekolah = it.child("lamaSekolah").value
                val kulkas = it.child("kulkas").value
                val menikah = it.child("menikah").value
                val rumahSewa = it.child("rumahSewa").value
                val tv = it.child("tv").value
                binding.tvNama.text = nama.toString()
                binding.tvPPKS.text = jenisppks.toString()
                binding.tvNik.text = nik
                binding.tvalamat.text = alamat.toString()
                binding.tvToilet.text = toilet.toString()
                binding.tvResult.text = result.toString()
                binding.tvBercerai.text = bercerai.toString()
                binding.tvDisabilitas.text = disabilitas.toString()
                binding.tvAnak12.text = jumlahanak12.toString()
                binding.tvAnak19.text = jumlahanak19.toString()
                binding.tvJumlahKeluarga.text = jumlahAnggota.toString()
                binding.tvLansia.text = jumlahLansia.toString()
                binding.tvTanggungan.text = jumlahTanggungan.toString()
                binding.tvKomputer.text = komputer.toString()
                binding.tvLamaSekolah.text = lamaSekolah.toString()
                binding.tvKulkas.text = kulkas.toString()
                binding.tvMenikah.text = menikah.toString()
                binding.tvRumahSewa.text = rumahSewa.toString()
                binding.tvTV.text = tv.toString()
            }else{
                Toast.makeText(this,"User Doesn't Exist", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun updateData(nik: String, status: String){
        databse = FirebaseDatabase.getInstance().getReference("Users")
        val user = mapOf<String, String>(
            "status" to status
        )

        databse.child(nik).updateChildren(user).addOnSuccessListener {
            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}