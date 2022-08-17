package com.example.nergconductor.adapter


import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.nergconductor.R
import com.example.nergconductor.model.RailroadCar

class RailAdapter(context: Context, val railcars: List<RailroadCar>):
    ListAdapter {
    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {

    }

    override fun getCount(): Int {
        return railcars.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =  LayoutInflater.from(parent.context).inflate(
            R.layout.passenger_item, parent,
            false)
        val railNo = view.findViewById<TextView>(R.id.railroad_no_tv)
        val occupancy = view.findViewById<TextView>(R.id.occupency_tv)
        railNo.text = railcars[position].railroadCarNumber.toString()
        occupancy.text = "${railcars[position].reservedSeats} / 25"
        return view
    }

    override fun getItemViewType(p0: Int): Int {
        return p0
    }

    override fun getViewTypeCount(): Int {
        return railcars.size
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEnabled(p0: Int): Boolean {
        return true
    }
}