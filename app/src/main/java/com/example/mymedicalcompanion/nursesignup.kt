package com.example.mymedicalcompanion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_doctorsignup.*

import kotlinx.android.synthetic.main.activity_doctorsignup.tildoctorid3
import kotlinx.android.synthetic.main.activity_nursesignup.*

class nursesignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nursesignup)
        supportActionBar?.title = "Nurse SignUp"
        btnnurselogin2.setOnClickListener {
            startActivity(Intent(this,login::class.java))
        }
        btnnursesignup2.setOnClickListener {
            signupusers()
        }


    }
    fun signupusers(){
        val email: String = tilnurseemail3.text.toString()
        val password: String = tilnursepassword3.text.toString()
        val mAuth= FirebaseAuth.getInstance()
        btnnursesignup2.setOnClickListener {

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
        val email: String = tilnurseemail3.text.toString()
        if (TextUtils.isEmpty(email)) {
            tilnurseemail2.error = "you cant leave this empty"
            return false
        } else {
            tilnurseemail2.isErrorEnabled=false
            return true
        }
    }

    fun passwordvalidation(): Boolean {
        val password: String = tilnursepassword3.text.toString()
        val confirmpassword=tilnurseconfirmpassword3.text.toString()
        if (TextUtils.isEmpty(password)) {
            tilnursepassword2.error = "you cant leave this empty"
            return false
        }
        else if (password!=confirmpassword)
        {
            tilnurseconfirmpassword2.error = "Passwords are not same"
            return false
        }
        else {
            tilnursepassword2.isErrorEnabled=false
            tilnurseconfirmpassword2.isErrorEnabled=false
            return true
        }
    }
    fun sendinginfotodatabase() {

        val password: String = tilnursepassword3.text.toString()
        val email: String = tilnurseemail3.text.toString()
        val id=tilnurseid3.text.toString()
        val databaseref = FirebaseDatabase.getInstance().getReference("Nurse").child(id)
        val userprofileinformation = nurseinfo(email, password,id)
        databaseref.setValue(userprofileinformation)}
    }


