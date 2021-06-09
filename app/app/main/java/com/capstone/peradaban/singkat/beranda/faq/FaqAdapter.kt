package com.capstone.peradaban.singkat.beranda.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.peradaban.singkat.databinding.ItemFaqBinding

class FaqAdapter(private val listFaq: ArrayList<FAQ>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FaqViewHolder {
        val binding = ItemFaqBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(listFaq[position])
    }

    override fun getItemCount(): Int = listFaq.size

    inner class FaqViewHolder(private val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FAQ) {
            with(binding){
                pertanyaanFaq.text = faq.pertanyaan
                jawabanFaq.text = faq.jawaban
            }
        }
    }
}