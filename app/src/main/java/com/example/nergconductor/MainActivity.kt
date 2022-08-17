package com.example.nergconductor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.nergconductor.databinding.ActivityMainBinding
import com.example.nergconductor.model.Connection
import com.example.nergpassenger.model.Line
import com.example.nergpassenger.model.LineDetail
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.time.Duration
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    var arrivalLine = ""
    var destLine = ""
    lateinit var origin:String
    lateinit var dest:String
    val hashLinesCodes: HashMap<String,List<String>> = HashMap()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val line11 = listOf("NB","NZ","LOE","OR","B","BER")
        val line12 = listOf("BER","B","OR","LOE","NZ","NB")
        val line21 = listOf("NB","MC","GUE","SN","HH")
        val line22 = listOf("HH","SN","GUE","MC","NB")
        val line2a1 = listOf("NB","MC","GUE","HRO")
        val line2a2 = listOf("HRO","GUE","MC","NB")
        val line31 = listOf("NB","AT","DM","HST","HRO")
        val line32 = listOf("HRO","HST","DM","AT","NB")
        val line41 = listOf("NB","PAS","SCZ")
        val line42 = listOf("SCZ","PAS","NB")
        val allLines = listOf(line12,line11,line22,line21,line2a2,line2a1,line32,line31,line42,
            line41)

        binding.getConBtn.setOnClickListener {
            origin = binding.originEt.text.toString()
            dest = binding.destEt.text.toString()
            Thread{
                val linesRes = Api.getLines(this)
                val linesObj = JSONArray(linesRes).toString()
                val lines = Json.decodeFromString<List<Line>>(linesObj)
                var counter = 0
                lines.forEach {
                    hashLinesCodes[it.id] = allLines[counter]
                    counter++
                }
                var same = false
                hashLinesCodes.forEach {
                    if(arrivalLine == "" && destLine ==""){
                        if(it.value.contains(origin) && it.value.contains(dest) ){
                            if(it.value.indexOf(origin) < it.value.indexOf(dest)){
                                arrivalLine = it.key
                                destLine = it.key
                                same =  true
                            }
                        }
                    }
                }
                if(same) {
                    val lineConRes = Api.getLines(this, arrivalLine)
                    val lineConObj = JSONObject(lineConRes).toString()
                    val lineCons = Json.decodeFromString<LineDetail>(lineConObj)
                    val cons = getSameLinesConnections(lineCons)
                    val trains = mutableListOf<String>()
                    cons.forEach {
                        trains.add(it.trainNumber)
                    }
                    Handler(mainLooper).post {
                        val adapter = ConnectionAdapter(this,R.layout.connections_item,trains, true)
                        binding.connectionsList.adapter = adapter
                    }
                }
        }.start()
        }
    }
    fun getSameLinesConnections(lineDetail: LineDetail): List<Connection> {
        val validConnections= mutableListOf<Connection>()
        lineDetail.connections.forEach {

        }
        lineDetail.connections.forEach {
            var both = 0
            it.stops?.forEach { stop ->
                if(stop.station.code == origin)
                    both++
                if(stop.station.code == dest)
                    both++
            }
            if(both == 2)
                validConnections.add(it)
        }
        return validConnections
    }
}