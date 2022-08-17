package com.example.nergconductor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nergconductor.databinding.ActivityTicketConfBinding
import com.example.nergconductor.model.Ticket
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class TicketConfActivity : AppCompatActivity() {
    lateinit var ticket: String
    lateinit var binding: ActivityTicketConfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketConfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent =  intent.extras
        ticket = intent?.get("Ticket").toString()


        val ticketObj = JSONObject(ticket).toString()
        val ticketDetails = Json.decodeFromString<Ticket>(ticketObj)

        binding.arrivalTimeTv.text = ticketDetails.segments[0].arrivalStop.time
        binding.deptTimeTv.text = ticketDetails.segments[0].departureStop.time

        binding.originTv.text = ticketDetails.segments[0].arrivalStop.station.name
        binding.destTv.text = ticketDetails.segments[0].arrivalStop.station.name

        binding.ticketNoTv.text = ticketDetails.ticketNumber
        binding.noPeopleTv.text = ticketDetails.passengers.size.toString()

        binding.personTv.text = ticketDetails.bookedBy.username

        if(!ticketDetails.loyalty.isNullOrBlank()){
            binding.loyalBtn.visibility = View.VISIBLE
            binding.loyalLbl.visibility = View.VISIBLE
        }

        binding.loyalBtn.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
        Thread{
            val valid = Api.getUserTicket(ticketDetails.ticketNumber,this)
            if(valid)
                binding.verifiedTV.text = "This Ticket is Valid"
            else
                binding.verifiedTV.text = "This ticket is not valid"
        }.start()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result  = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result !=null){
            if((result.contents != null))
            {
                AlertHelper.showAlert(this,"Loyalty Card Check")

            }
        }
    }
}