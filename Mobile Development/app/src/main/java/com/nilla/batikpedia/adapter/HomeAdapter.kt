package com.example.capstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.model.Item
import com.nilla.batikpedia.R

class HomeAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        val imageView: ImageView = itemView.findViewById(R.id.ivCard)
        val titleTextView: TextView = itemView.findViewById(R.id.tvCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.dateTextView.text = currentItem.date
        holder.imageView.setImageResource(currentItem.imageResId)
        holder.titleTextView.text = currentItem.title
    }

    override fun getItemCount() = itemList.size
}