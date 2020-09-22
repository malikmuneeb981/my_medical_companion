package com.example.mymedicalcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        btnsignupdoctor.setOnClickListener {
            startActivity(Intent(this,doctorsignup::class.java))
        }
        btnsignuppatient.setOnClickListener {
            startActivity(Intent(this,patientsignup::class.java))
        }
        btnsignupnurse.setOnClickListener {
            startActivity(Intent(this,nursesignup::class.java))
        }

    }
}