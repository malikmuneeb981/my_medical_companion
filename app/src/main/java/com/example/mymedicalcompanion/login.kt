package com.example.mymedicalcompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctorsignup.*
import kotlinx.android.synthetic.main.activity_login.*


class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Patient LogIn"
        val firebaseAuth=FirebaseAuth.getInstance()
        btnlogin1.setOnClickListener {
            val email=tilemail4.text.toString()
            val password=tilpassword4.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,
                OnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    startActivity(Intent(this,doctorsactivity::class.java))
                }
                else
                {
                    Toast.makeText(this,"incorrect password or email",Toast.LENGTH_SHORT).show()
                }
            })

        }

        btnforgotpassword1.setOnClickListener {
            /* val usertype = optionselectlogin2.text.toString()
            val userid = tiluserid4.text.toString()
            val firebaseAuth=FirebaseAuth.getInstance()
            val ref = FirebaseDatabase.getInstance().reference.child(usertype).child(firebaseAuth.uid)
                ref.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        val currentuser=p0.getValue(userinfo::class.java)
                        Log.d("details","id ${currentuser!!.userid}")



                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        Log.d("failes","failed")
                    }
                })

        }*/
        }

    }
}