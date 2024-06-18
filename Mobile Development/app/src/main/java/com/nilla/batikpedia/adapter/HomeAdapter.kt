package com.example.capstone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.ui.DetailBeritaActivity
import com.nilla.batikpedia.R
import com.nilla.batikpedia.response.NewsItem
import com.squareup.picasso.Picasso

class HomeAdapter(private var itemList: List<NewsItem>, private val context: Context) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

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
        holder.dateTextView.text = currentItem.timestamp
        Picasso.get().load(currentItem.imageUrl).into(holder.imageView)
        holder.titleTextView.text = currentItem.judul

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailBeritaActivity::class.java)
            intent.putExtra("NEWS_ID", currentItem.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = itemList.size

    fun updateData(newItemList: List<NewsItem>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}