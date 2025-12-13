package com.example.datatransfer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class OrderNow : AppCompatActivity() {

companion object{
    const val KEY= "com.example.datatransfer.OrderNow.KEY"
}

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_now)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val order= findViewById<Button>(R.id.btnOrder)

        order.setOnClickListener {
            val message =
                findViewById<EditText>(R.id.eT1).text.toString() + "\n" + findViewById<EditText>(R.id.eT2).text.toString() + "\n" + findViewById<EditText>(
                    R.id.eT3
                ).text.toString() + "\n" + findViewById<EditText>(R.id.eT4).text.toString()
           val alert = AlertDialog.Builder(this)
            alert.setTitle("Are you sure")
            alert.setMessage("Do you want to order your preferred foods/Drinks?")
            alert.setIcon(R.drawable.outline_fastfood_24)

            alert.setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, showOrder::class.java)
                intent.putExtra(KEY, message)
                startActivity(intent)
            }
            alert.setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "Order Cancelled", Toast.LENGTH_SHORT).show()
            }
            alert.show()


        }
    }
}