package com.example.nergconductor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nergconductor.R
import com.example.nergconductor.model.RailroadCar

class RailCarAdapter(val railcars: List<RailroadCar>) : RecyclerView.Adapter<RailCarAdapter
.RailCarHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RailCarHolder {
        context = parent.context
        return RailCarHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.passenger_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RailCarHolder, position: Int) {
        holder.bind(railcars, position, context)
    }

    override fun getItemCount(): Int {
        return railcars.size
    }

    class RailCarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val railNo = itemView.findViewById<TextView>(R.id.railroad_no_tv)
        val occupancy = itemView.findViewById<TextView>(R.id.occupency_tv)
        fun bind(railcars: List<RailroadCar>, pos: Int, context: Context) {
            railNo.text = railcars[pos].railroadCarNumber.toString()
            occupancy.text = "${railcars[pos].reservedSeats} / 25"
        }
    }

}