package com.example.astrobrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val mydata: ArrayList<Data>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView) {
        val text1: TextView = cardView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.card,
            parent, false
        )
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text1.text = mydata[position].description
    }

    override fun getItemCount(): Int {
        return (mydata.size)
    }
}