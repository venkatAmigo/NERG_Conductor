package com.example.nergconductor

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView

class ConnectionAdapter(val context:Context, val layout: Int, val connections: List<String>, val
same: Boolean):
    ListAdapter {
    override fun registerDataSetObserver(p0: DataSetObserver?) {

    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {

    }

    override fun getCount(): Int {
        return connections.size
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
        val view =  LayoutInflater.from(parent.context).inflate(layout, parent, false)
        val card = view.findViewById<CardView>(R.id.conOne)
        val train = view.findViewById<TextView>(R.id.trainTv)

        Log.i("ADAPTER",connections.toString())
        train.text = connections[position]

        card.setOnClickListener {
            val sharedPreferences = parent.context.getSharedPreferences("CONDUCTOR", Context
                .MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.putString("Connection", connections[position])
            edit.apply()
            val intent = Intent(context,StatisticsActivity::class.java)
            intent.putExtra("train",train.text)
            parent.context.startActivity(intent)
        }
        return view
    }

    override fun getItemViewType(p0: Int): Int {
        return p0
    }

    override fun getViewTypeCount(): Int {
        return connections.size
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