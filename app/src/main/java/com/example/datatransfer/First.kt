package com.example.datatransfer

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import android.content.Intent
import com.example.datatransfer.databinding.ActivityFirstBinding


class First : AppCompatActivity() {
    lateinit var binding: ActivityFirstBinding
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         binding= ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignUp.setOnClickListener {
            val  username= binding.etName.text.toString()
            val  email= binding.etMail.text.toString()
            val  pass= binding.etPassword.text.toString()
            val  rePass= binding.etRePassword.text.toString()



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
            if(!binding.checkBox.isChecked){
                binding.checkBox.buttonTintList = getColorStateList(R.color.hotpink)
             Toast.makeText(this,"Please accept the terms and conditions",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
       binding.tvSignIn.setOnClickListener {
           intent= Intent(this,signIn::class.java)
           startActivity(intent)
       }

    }
}