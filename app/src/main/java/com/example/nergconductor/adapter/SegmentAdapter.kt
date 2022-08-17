package com.example.nergconductor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nergconductor.R
import com.example.nergconductor.model.FareDodger
import com.example.nergconductor.model.RailroadCar
import com.example.nergconductor.model.Reservation
import com.example.nergconductor.model.SeatReservations

class SegmentAdapter(val reservations: List<Reservation>) : RecyclerView.Adapter<SegmentAdapter
.SegmentHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SegmentHolder {
        context = parent.context
        return SegmentHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.segment_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SegmentHolder, position: Int) {
        holder.bind(reservations, position, context)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    class SegmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val railsRecyclerView: RecyclerView= itemView.findViewById(R.id.railroad_recycler)
        val segNo: TextView= itemView.findViewById(R.id.segment_no_tv)
        fun bind(reservations: List<Reservation>, pos: Int, context: Context) {
            val adapter = RailCarAdapter(reservations[pos].railroadCars)
            railsRecyclerView.layoutManager = LinearLayoutManager(context,RecyclerView
                .HORIZONTAL,false)
            railsRecyclerView.adapter = adapter
            val segment =  "${reservations[pos].fromStation} - ${reservations[pos].toStation}"
            segNo.text = segment
        }
    }

}