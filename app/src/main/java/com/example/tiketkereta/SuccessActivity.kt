package com.example.tiketkereta

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val varNama: TextView = findViewById(R.id.var_nama)
        val varJam: TextView = findViewById(R.id.var_jam)
        val varTanggal: TextView = findViewById(R.id.var_tanggal)
        val varTujuan: TextView = findViewById(R.id.var_tujuan)

        // Retrieve data from intent
        val name = intent.getStringExtra("NAME") ?: "Tidak ada nama"
        val time = intent.getStringExtra("TIME") ?: "Tidak ada jam"
        val date = intent.getStringExtra("DATE") ?: "Tidak ada tanggal"
        val destination = intent.getStringExtra("DESTINATION") ?: "Tidak ada tujuan"

        // Set the values in the TextViews
        varNama.text = name
        varJam.text = time
        varTanggal.text = date
        varTujuan.text = destination
    }
}
