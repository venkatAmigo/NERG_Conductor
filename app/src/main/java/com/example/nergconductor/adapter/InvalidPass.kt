package com.example.nergconductor.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.nergconductor.R
import com.example.nergconductor.model.FareDodger
import com.example.nergconductor.model.Ticket
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.math.roundToInt


class InvalidPass(val fareDodgers: List<FareDodger>) : RecyclerView.Adapter<InvalidPass
.InvalidHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvalidHolder {
        context = parent.context
        return InvalidHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.invalid_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: InvalidHolder, position: Int) {
        holder.bind(fareDodgers, position, context)
    }

    override fun getItemCount(): Int {
        return fareDodgers.size
    }

    class InvalidHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day_no_tv)
        val daily: TextView = itemView.findViewById(R.id.daily_no_tv)
        val percent: TextView = itemView.findViewById(R.id.percentage_no_tv)
        val card : CardView = itemView.findViewById(R.id.ticket_card)

        fun bind(fareDodgers: List<FareDodger>, pos: Int, context: Context) {
            day.text = fareDodgers[pos].date
            val without = fareDodgers[pos].passengerCountWithoutTicket
            daily.text = without.toString()
            var total:Double = (without + fareDodgers[pos]
                .passengerCountWithTicket).toDouble()
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            percent.text = "${df.format((without / total) * 100)} %"
            card.setOnClickListener {
                card.animate().rotation(360.0f).setDuration(1000)
//                val builder = AlertDialog.Builder(context)
//                builder.setView(R.layout.invalid_item)
//                builder.show()
            }

        }
    }

}