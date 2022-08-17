package com.example.nergconductor

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class Api {
    companion object {

        fun  getUserTicket(id: String, context: Context): Boolean {
            var response = ""
            val url = URL("http://10.0.2.2:3000/users/tickets/$id")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            Log.i("RESPONSE", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.inputStream.bufferedReader(
                    StandardCharsets
                        .UTF_8
                ).use {
                    it.readText()
                }
                return true
            } else {
                Handler(Looper.getMainLooper()).post {

                }
            }
            return false
        }
        fun  getStations(context: Context): String {
            var response = ""
            var url= URL("http://10.0.2.2:3000/stations")

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            Log.i("RESPONSE", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.inputStream.bufferedReader(
                    StandardCharsets
                        .UTF_8
                ).use {
                    it.readText()
                }
                return response
            } else {
                Handler(Looper.getMainLooper()).post {
                    AlertHelper.showAlert(context, "Error", httpURLConnection.responseMessage)
                }
            }
            return response
        }

        fun  getLines(context: Context, line : String?=""): String {
            var response = ""
            var url: URL = if(line != ""){
                URL("http://10.0.2.2:3000/lines/$line")
            }else{
                URL("http://10.0.2.2:3000/lines")
            }
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            Log.i("RESPONSE", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.inputStream.bufferedReader(
                    StandardCharsets
                        .UTF_8
                ).use {
                    it.readText()
                }
                return response
            } else {
                Handler(Looper.getMainLooper()).post {
                    AlertHelper.showAlert(context, "Error", httpURLConnection.responseMessage)
                }
            }
            return response
        }
        fun  getFareDodgers(context: Context): String {
            var response = ""
            var url= URL("http://10.0.2.2:3000/statistics/fare-dodgers?from=2022-05-26&to=2022-08-16")

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            Log.i("RESPONSE", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.inputStream.bufferedReader(
                    StandardCharsets
                        .UTF_8
                ).use {
                    it.readText()
                }
                Log.i("RESPONSE",response)
                return response
            } else {
                Handler(Looper.getMainLooper()).post {
                    // AlertHelper.showAlert(context, "Error", httpURLConnection.responseMessage)
                }
            }
            return response
        }
        fun  getSeatReservations(context: Context, trainNo: String,date:String): String {
            var response = ""
            var url= URL("http://10.0.2.2:3000/statistics/seat-reservations?date=2022-06-01&trainNumber=30203")

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = false

            val responseCode = httpURLConnection.responseCode
            Log.i("RESPONSE", responseCode.toString())
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = httpURLConnection.inputStream.bufferedReader(
                    StandardCharsets
                        .UTF_8
                ).use {
                    it.readText()
                }
                Log.i("RESPONSE", response.toString())
                return response
            } else {
                Handler(Looper.getMainLooper()).post {
                    AlertHelper.showAlert(context, "Error", httpURLConnection.responseMessage)
                }
            }
            return response
        }
    }
}