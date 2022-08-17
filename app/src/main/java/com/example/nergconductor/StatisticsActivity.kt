package com.example.nergconductor

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nergconductor.Api.Companion.getFareDodgers
import com.example.nergconductor.Api.Companion.getSeatReservations
import com.example.nergconductor.adapter.InvalidPass
import com.example.nergconductor.adapter.SegmentAdapter
import com.example.nergconductor.databinding.ActivityStatisticsBinding
import com.example.nergconductor.model.FareDodger
import com.example.nergconductor.model.Reservation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.time.LocalDateTime


class StatisticsActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatisticsBinding
    lateinit var reservationsOut :List<Reservation>
    lateinit var fareDodgerOut:List<FareDodger>
    lateinit var invalidAdapter : InvalidPass
    lateinit var segmentAdapter: SegmentAdapter
    lateinit var handler: Handler
    var isNetAvailable: Boolean = false
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
            handler = Handler(mainLooper)
            invalidAdapter = InvalidPass(listOf())
            binding.invalidRecycler.layoutManager = GridLayoutManager(this,3)
            binding.segmentRecyler .layoutManager  = LinearLayoutManager(this,RecyclerView
                .VERTICAL,false)
            binding.segmentRecyler.adapter = SegmentAdapter(listOf())


            val sharedPreferences = getSharedPreferences("CONDUCTOR", Context.MODE_PRIVATE)
            var ress = sharedPreferences.getString("reservations","")
            var fares = sharedPreferences.getString("faredodgers","")
            if(ress != "" && fares != ""){
                fareDodgerOut = fares?.let { Json.decodeFromString(it) }!!
                reservationsOut = ress?.let { Json.decodeFromString(it) }!!
                segmentAdapter = SegmentAdapter(reservationsOut)
                binding.segmentRecyler.adapter = segmentAdapter
                invalidAdapter = InvalidPass(fareDodgerOut)
                binding.invalidRecycler.adapter = invalidAdapter
            }
            val con = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            val networkCallback = object:
                ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isNetAvailable = true
                    getFareDodger()
                    getSeatReservations()
                    Handler(mainLooper).post {
                        binding.lastSyncTv.visibility = View.INVISIBLE
                    }
                }
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    if(networkCapabilities.hasCapability(NetworkCapabilities
                            .NET_CAPABILITY_INTERNET))
                        Toast.makeText(this@StatisticsActivity, "Network Available", Toast
                            .LENGTH_SHORT).show()

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isNetAvailable = false
                    Handler(mainLooper).post {
                        binding.lastSyncTv.visibility = View.VISIBLE
                    }
                }

            }
            con.requestNetwork(request, networkCallback)
            binding.lastSyncTv.text = "LastSync \n"+prefs.getString("LAST_SYNC","")
            if(isNetAvailable) {
                getFareDodger()
                getSeatReservations()

            }else{
                binding.lastSyncTv.visibility = View.VISIBLE
            }
            while(isNetAvailable) {
                val runnableCode: Runnable = object : Runnable {
                    override fun run() {
//                        getFareDodger()
//                        getSeatReservations()
                        handler.postDelayed(this, 2000)
                    }
                }
                handler.post(runnableCode)
            }
            binding.navConBtn.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }

    }

    fun getSeatReservations(){
        Thread{
            val resrvRes = Api.getSeatReservations(this, "", "")
            val resrvObj = JSONArray(resrvRes).toString()
            prefs.putAny("LAST_SYNC",LocalDateTime.now().toString())
            val reservations = Json.decodeFromString<List<Reservation>>(resrvObj)
            reservationsOut = reservations
            Handler(mainLooper).post {
                segmentAdapter = SegmentAdapter(reservations)
                binding.segmentRecyler.adapter = segmentAdapter
            }
        }.start()
    }
    fun getFareDodger(){
        Thread {
            val fareRes = getFareDodgers(this)
            val fareObj = JSONArray(fareRes).toString()
            val fareDodgers = Json.decodeFromString<List<FareDodger>>(fareObj)
            prefs.putAny("LAST_SYNC",LocalDateTime.now().toString())
            fareDodgerOut = fareDodgers
            Handler(mainLooper).post {
                invalidAdapter = InvalidPass(fareDodgers)
                binding.invalidRecycler.adapter = invalidAdapter
            }
        }.start()
    }
}