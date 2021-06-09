package com.capstone.peradaban.singkat.ajukan


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.beranda.daftar.User
import com.capstone.peradaban.singkat.databinding.ActivityPendaftaranBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*

class PendaftaranActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPendaftaranBinding
    private lateinit var database: DatabaseReference
    var selectedImagePath: String? = null
    var selectedImageDapur: String? = null
    var selectedImageTidur: String? = null

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendaftaranBinding.inflate(layoutInflater)
        val listPPKS = resources.getStringArray(R.array.jenis_ppks)
        val arrayAdapter = ArrayAdapter(
            this@PendaftaranActivity,
            R.layout.item_jenis_ppks,
            listPPKS
        )
        binding.jenisPpks.setAdapter(arrayAdapter)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this, PhoneAuthActivity::class.java))
            finish()
        }

        val kirim = binding.idRegister

        binding.btnTanggalLahir.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
        }

        kirim.setOnClickListener {
            val jenisppks = binding.jenisPpks.text.toString()
            val fullname = binding.edNama.text.toString()
            val nik = binding.edKtp.text.toString()
            val nokk = binding.edKk.text.toString()
            val alamatKtp = binding.edAlamatktp.text.toString()
            val alamatSekarang = binding.edAlamatsekarang.text.toString()
            val tempatLahir = binding.edTempatLahir.text.toString()
            val tglLahir = binding.btnTanggalLahir.hint.toString()
            val radioGroup: RadioGroup = binding.jenisKelamin
            val selectJK: Int = radioGroup.checkedRadioButtonId
            val status = "oncheck"
            val resultML = "sample result"
            val rgToilet: RadioGroup = binding.toilet
            val rgKulkas: RadioGroup = binding.kulkas
            val rgTv: RadioGroup = binding.televisi
            val rgKomputer: RadioGroup = binding.komputer
            val rgMenikah: RadioGroup = binding.menikah
            val rgBercerai: RadioGroup = binding.bercerai
            val rgRumahSewa: RadioGroup = binding.rumahSewa
            val rgDisabilitas: RadioGroup = binding.disabilitas
            val selectToilet: Int = rgToilet.checkedRadioButtonId
            val selectKulkas: Int = rgKulkas.checkedRadioButtonId
            val selectTv: Int = rgTv.checkedRadioButtonId
            val selectKomputer: Int = rgKomputer.checkedRadioButtonId
            val selectMenikah: Int = rgMenikah.checkedRadioButtonId
            val selectBercerai: Int = rgBercerai.checkedRadioButtonId
            val selectRumahSewa: Int = rgRumahSewa.checkedRadioButtonId
            val selectDisabilitas: Int = rgDisabilitas.checkedRadioButtonId
            val jumlahAnggotaKeluarga = binding.edJumlahAnggota.text.toString()
            val jumlahLansia = binding.edJumlahLansia.text.toString()
            val jumlahAnak19 = binding.edJumlahAnak19.text.toString()
            val jumlahAnak12 = binding.edJumlahAnak12.text.toString()
            val jumlahTanggungan = binding.edJumlahTanggungan.text.toString()
            val lamaSekolah = binding.edLamaSekolah.text.toString()




            if ((selectJK!= -1) || (selectToilet!= -1) || (selectTv != -1) || (selectBercerai != -1) || (selectDisabilitas != -1) || (selectKomputer != -1) || (selectKulkas != -1) || (selectMenikah != -1) || (selectRumahSewa != -1)) {
                val radio: RadioButton = findViewById(selectJK)
                val rdToilet: RadioButton = findViewById(selectToilet)
                val rdKulkas: RadioButton = findViewById(selectKulkas)
                val rdTv: RadioButton = findViewById(selectTv)
                val rdKomputer: RadioButton = findViewById(selectKomputer)
                val rdMenikah: RadioButton = findViewById(selectMenikah)
                val rdBercerai: RadioButton = findViewById(selectBercerai)
                val rdRumahSewa: RadioButton = findViewById(selectRumahSewa)
                val rdDisablilitas: RadioButton = findViewById(selectDisabilitas)

                val gender = radio.text.toString()
                val toilet = rdToilet.text.toString()
                val kulkas = rdKulkas.text.toString()
                val tv = rdTv.text.toString()
                val komputer = rdKomputer.text.toString()
                val menikah = rdMenikah.text.toString()
                val bercerai = rdBercerai.text.toString()
                val rumahSewa = rdRumahSewa.text.toString()
                val disablilitas = rdDisablilitas.text.toString()


                val noTelp = binding.edTelepon.text.toString()

                database = FirebaseDatabase.getInstance().getReference("Users")
                val user = User(
                    jenisppks,
                    fullname,
                    nik,
                    nokk,
                    alamatKtp,
                    alamatSekarang,
                    tempatLahir,
                    tglLahir,
                    gender,
                    noTelp,
                        status,
                        resultML,
                        toilet,
                        kulkas,
                        tv,
                        komputer,
                        menikah,
                        bercerai,
                        rumahSewa,
                        disablilitas,
                        jumlahAnggotaKeluarga,
                        jumlahLansia,
                        jumlahAnak19,
                        jumlahAnak12,
                        jumlahTanggungan,
                        lamaSekolah
                )
                if (nik.isNotEmpty()){
                    database.child(nik).setValue(user).addOnCompleteListener {
                        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
//                        auth.signOut()
//                        startActivity(Intent(this@PendaftaranActivity,UploadFotoActivity::class.java))
//                        finish()
                        val intent = Intent(this@PendaftaranActivity, UploadFotoActivity::class.java)
                        intent.putExtra(UploadFotoActivity.KEY, nik.toString())
                        startActivity(intent)

                    }.addOnFailureListener {
                        Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }else if(nik.isEmpty()){
                    Toast.makeText(this, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }

            }
        }



    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        binding.btnTanggalLahir.hint = dateFormat.format(calendar.time)
    }


}