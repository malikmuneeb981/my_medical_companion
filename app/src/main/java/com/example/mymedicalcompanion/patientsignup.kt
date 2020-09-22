package com.example.mymedicalcompanion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_doctorsignup.*
import kotlinx.android.synthetic.main.activity_patientsignup.*

class patientsignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientsignup)
        supportActionBar?.title="Patient SignUp"
        val myauth = FirebaseAuth.getInstance()
        btnlogin2.setOnClickListener {
    startActivity(Intent(this,login::class.java))
        }
        btnpatientsignup2.setOnClickListener {
            if (emailvalidation() && passwordvalidation()) {
                val dialog = ACProgressFlower.Builder(this).direction(
                    ACProgressConstant.DIRECT_CLOCKWISE
                )
                    .themeColor(Color.WHITE)
                    .text("Registering user please wait..")
                    .textSize(15)
                    .isTextExpandWidth(true)
                    .fadeColor(Color.DKGRAY).build()
                dialog.show()
                val patientemail: String = tilpatientemail3.text.toString()
                val patientpassword = tilpatientpassword3.text.toString()
                myauth.createUserWithEmailAndPassword(patientemail, patientpassword)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            sendinginfotodatabase()
                            dialog.dismiss()
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

    }

    fun emailvalidation(): Boolean {
        val email: String = tilpatientemail3.text.toString()
        if (TextUtils.isEmpty(email)) {
            tilpatientemail2.error = "you cant leave this empty"
            return false
        } else {
            tilpatientemail2.isErrorEnabled = false
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilpatientpassword3.text.toString()
        val confirmpassword = tilpatientconfirmpassword3.text.toString()
        if (TextUtils.isEmpty(password)) {
            tilpatientpassword2.error = "you cant leave this empty"
            return false
        } else if (password != confirmpassword) {
            tilpatientconfirmpassword2.error = "Passwords are not same"
            return false
        } else {
            tilpatientpassword2.isErrorEnabled = false
            tilpatientconfirmpassword2.isErrorEnabled = false
            return true
        }
    }
    fun sendinginfotodatabase() {

        val password: String = tilpatientpassword3.text.toString()
        val email: String = tilpatientemail3.text.toString()
        val databaseref = FirebaseDatabase.getInstance().getReference("patients").child(FirebaseAuth.getInstance().uid)
        val userprofileinformation = patientinfo(email, password)
        databaseref.setValue(userprofileinformation)}
}
