package com.capstone.peradaban.admin_singkat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.peradaban.admin_singkat.databinding.ItemStatusBinding

class UserAdapter(private val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
//        vView)al itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_status, parent, false)
//        return MyViewHolder(item
//        val ItemStatusBinding = ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(ItemStatusBinding)
        val itemStatusBinding = ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemStatusBinding)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.nik.text = currentItem.nik
        holder.fullname.text = currentItem.fullname
        holder.jenisppks.text = currentItem.jenisppks
        if (currentItem.status == "approved"){
            holder.status.setImageResource(R.drawable.diterima)
        }else if (currentItem.status == "rejected"){
            holder.status.setImageResource(R.drawable.ditolak)
        }else if (currentItem.status == "oncheck"){
            holder.status.setImageResource(R.drawable.diperiksa)
        }
        holder.bind(currentItem.nik.toString())



    }

    override fun getItemCount(): Int {
        return userList.size
    }

//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val nik: TextView = itemView.findViewById(R.id.tv_id_ktp)
//        val fullname: TextView = itemView.findViewById(R.id.tv_title)
//        val jenisppks: TextView = itemView.findViewById(R.id.tv_description)
//        val status: ImageView = itemView.findViewById(R.id.img_poster)
//
//    }

    inner class MyViewHolder(private val binding: ItemStatusBinding): RecyclerView.ViewHolder(binding.root){
        val nik: TextView = itemView.findViewById(R.id.tv_id_ktp)
        val fullname: TextView = itemView.findViewById(R.id.tv_title)
        val jenisppks: TextView = itemView.findViewById(R.id.tv_description)
        val status: ImageView = itemView.findViewById(R.id.img_poster)
        fun bind(data: String){
            with(binding){
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY, data)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

}