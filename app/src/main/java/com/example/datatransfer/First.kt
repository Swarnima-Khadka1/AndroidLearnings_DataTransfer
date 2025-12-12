package com.example.datatransfer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import android.content.Intent


class First : AppCompatActivity() {

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userName= findViewById<TextInputEditText>(R.id.etName)
        val mail= findViewById<TextInputEditText>(R.id.etMail)
        val password= findViewById<TextInputEditText>(R.id.etPassword)
        val rePassword= findViewById<TextInputEditText>(R.id.etRePassword)
        val btnSign= findViewById<Button>(R.id.btnSignUp)
        val tvSign= findViewById<TextView>(R.id.tvSignIn)


        btnSign.setOnClickListener {
            val  username= userName.text.toString()
            val  email= mail.text.toString()
            val  pass= password.text.toString()
            val  rePass= rePassword.text.toString()

            // Reset previous errors
            password.error = null
            rePassword.error = null

            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

            if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

            if (rePass.isEmpty()) {
                Toast.makeText(this, "Please re-type your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

            if (pass != rePass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }
            Toast.makeText(this,"Validation successful! Registering user...",Toast.LENGTH_SHORT).show()

            val user= User(username,email,pass,rePass)
            database= FirebaseDatabase.getInstance().getReference("Users")
            database.child(username).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"User Registered Successfully",Toast.LENGTH_SHORT).show()

                val intent = Intent(this, signIn::class.java)
                startActivity(intent)
                finish()

            } .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }


        }
       tvSign.setOnClickListener {
           intent= Intent(this,signIn::class.java)
           startActivity(intent)
       }

    }
}