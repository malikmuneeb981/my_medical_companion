package com.example.mymedicalcompanion

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_doctorsignup.*

class doctorsignup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Doctor SignUp"
        setContentView(R.layout.activity_doctorsignup)
        //btndoctorlogin2.setOnClickListener {
            //startActivity(Intent(this,login::class.java))
        //}
        btndoctorsignup2.setOnClickListener {
            signupusers()
        }


    }
    fun signupusers(){
        val email: String = tilemail3.text.toString()
        val password: String = tilpassword3.text.toString()
        val mAuth=FirebaseAuth.getInstance()
        btndoctorsignup2.setOnClickListener {

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
                mAuth.createUserWithEmailAndPassword(email, password)
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
        val email: String = tilemail3.text.toString()
        if (TextUtils.isEmpty(email)) {
            tilemail2.error = "you cant leave this empty"
            return false
        } else {
            tilemail2.isErrorEnabled=false
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilpassword3.text.toString()
        val confirmpassword=tilconfirmpassword3.text.toString()
        if (TextUtils.isEmpty(password)) {
            tilpassword2.error = "you cant leave this empty"
            return false
        }
        else if (password!=confirmpassword)
        {
            tilconfirmpassword2.error = "Passwords are not same"
            return false
        }
        else {
            tilpassword2.isErrorEnabled=false
            tilconfirmpassword2.isErrorEnabled=false
            return true
        }
    }
    fun sendinginfotodatabase() {

        val password: String = tilpassword3.text.toString()
        val email: String = tilemail3.text.toString()
        val id=tildoctorid3.text.toString()
        val databaseref = FirebaseDatabase.getInstance().getReference("Doctor").child(id)
        val userprofileinformation = userinfo(email, password,id)
        databaseref.setValue(userprofileinformation)}
    }

