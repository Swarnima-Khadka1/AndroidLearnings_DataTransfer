package com.example.datatransfer

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import android.content.Intent




class signIn : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signIn = findViewById<Button>(R.id.btnSignIn)
        val name = findViewById<TextInputEditText>(R.id.etName)
        val password = findViewById<TextInputEditText>(R.id.etPassword)

        signIn.setOnClickListener {
            //Take ref till Users
            val userName = name.text.toString()
            val pass = password.text.toString()
            if (userName.isNotEmpty() && pass.isNotEmpty()) {
                readData(userName, pass)
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun readData(userName: String, pass: String) {
        databaseReference= com.google.firebase.database.FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(userName).get().addOnSuccessListener { dataSnapshot ->

            if(dataSnapshot.exists()) {
                val correctPassword = dataSnapshot.child("password").value.toString()

                if (correctPassword == pass) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, OrderNow::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "User doesn't exists. Please signUp first.", Toast.LENGTH_SHORT).show()
        }
    }
}