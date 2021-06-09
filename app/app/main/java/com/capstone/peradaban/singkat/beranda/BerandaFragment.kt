package com.capstone.peradaban.singkat.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.peradaban.singkat.ajukan.PhoneAuthActivity
import com.capstone.peradaban.singkat.ajukan.UploadFotoActivity
import com.capstone.peradaban.singkat.beranda.faq.FaqActivity
import com.capstone.peradaban.singkat.beranda.hubungi.HubungiActivity
import com.capstone.peradaban.singkat.databinding.FragmentBerandaBinding
import com.google.firebase.database.FirebaseDatabase


/**
 * A simple [Fragment] subclass.
 * Use the [BerandaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvPendaftaran.setOnClickListener {
            val intent = Intent(context, PhoneAuthActivity::class.java)
            startActivity(intent)
        }
        binding.cvStatus.setOnClickListener {
            val intent = Intent(context, FaqActivity::class.java)
            startActivity(intent)
        }
        binding.cvHubungi.setOnClickListener {
            val intent = Intent(context, HubungiActivity::class.java)
            startActivity(intent)
        }

    }
}