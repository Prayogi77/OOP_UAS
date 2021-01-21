package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crud.Database.Konsumen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_produsen.setOnClickListener {
            val intent = Intent(this, ProdusenActivity::class.java)
            startActivity(intent)
        }
        btn_konsumen.setOnClickListener {
            val intent = Intent(this, KonsumenActivity::class.java)
            startActivity(intent)
        }
    }
}