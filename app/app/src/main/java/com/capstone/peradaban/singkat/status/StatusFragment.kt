package com.capstone.peradaban.singkat.status

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.databinding.FragmentBerandaBinding
import com.capstone.peradaban.singkat.databinding.FragmentStatusBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.annotations.NotNull


class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatusBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        binding.contentStatus.visibility = View.INVISIBLE


        binding.readnikBtn.setOnClickListener {
            val nik: String = binding.etNik.text.toString()
            if (nik.isNotEmpty()){
                binding.contentStatus.visibility = View.VISIBLE
                Log.d("MAIN_ES", "$nik")
                readData(nik)
            }
            else{
                Log.d("MAIN_ES", "ERROR NIK")
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
                val status = it.child("status").value
                binding.tvIdKtp.text = "NIK: "+nik.toString()
                binding.tvTitle.text = fullname.toString()
                binding.tvDescription.text = "Jenis PPKS: "+jenisPPks.toString()
                Log.d("MAIN_ES", "${status.toString()}")
                if (status.toString() == "approved"){
                    binding.imgPoster.setImageResource(R.drawable.diterima)
                    binding.cvPendaftaran.setOnClickListener {
                        val intent = Intent(context, DetailStatusActivity::class.java)
                        intent.putExtra(DetailStatusActivity.KEY, nik.toString())
                        context?.startActivity(intent)
                    }
                }else if (status.toString() == "rejected"){
                    binding.imgPoster.setImageResource(R.drawable.ditolak)
                }else if(status.toString() == "oncheck"){
                    binding.imgPoster.setImageResource(R.drawable.diperiksa)
                }
            }else{
                binding.tvIdKtp.text = "Data kosong"
                binding.tvTitle.text = "Data kosong"
                binding.tvDescription.text = "Data kosong"
            }
        }.addOnFailureListener {
           Log.d("MAIN_ES","ERROR")
        }

    }
}