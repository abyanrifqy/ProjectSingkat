package com.capstone.peradaban.singkat.beranda.faq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.databinding.ActivityFaqBinding


class FaqActivity : AppCompatActivity() {

    private lateinit var faqAdapter: FaqAdapter
    private lateinit var binding: ActivityFaqBinding
    private val list = ArrayList<FAQ>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Kumpulan Pertanyaan"
        list.addAll(getListHeroes())
        faqAdapter = FaqAdapter(list)

        with(binding.rvFaq) {
            layoutManager = LinearLayoutManager(this@FaqActivity)
            setHasFixedSize(true)
            adapter = faqAdapter
        }
    }

    private fun getListHeroes(): ArrayList<FAQ> {
        val dataPertanyaan = resources.getStringArray(R.array.data_pertanyaan)
        val dataJawaban = resources.getStringArray(R.array.data_jawaban)

        val listHero = ArrayList<FAQ>()
        for (position in dataPertanyaan.indices) {
            val faq = FAQ(
                dataPertanyaan[position],
                dataJawaban[position]
            )
            listHero.add(faq)
        }
        return listHero
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return  super.onSupportNavigateUp()
    }
}