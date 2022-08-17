package com.example.nergconductor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nergconductor.databinding.ActivityMainBinding
import com.example.nergconductor.databinding.ActivityQrCodeScanBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanOptions

class QrCodeScanActivity : AppCompatActivity() {
    lateinit var binding:ActivityQrCodeScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrCodeScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanBtn.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                integrator.initiateScan()
        }

        binding.navConBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result  = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result !=null){
            if((result.contents != null))
            {
                Log.i("DETAILS",result.contents.toString())
                val intent = Intent(this,TicketConfActivity::class.java)
                intent.putExtra("Ticket",result.contents)
                startActivity(intent)
            }
        }
    }
}