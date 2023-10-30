package com.example.firebaserealtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.firebaserealtime.databinding.ActivityMainBinding
import com.example.firebaserealtime.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //    private lateinit var database: DatabaseReference
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var number = 1
        binding.btn.setOnClickListener {
            number++
            LoadData(number)
        }

        val database = Firebase.database
        val myRef = database.getReference("100-test/Users:")

        myRef.get().addOnSuccessListener { dataSnapshot ->
            val email = dataSnapshot.value.toString()
            binding.textSingle.text = "Value: $email"
        }.addOnFailureListener {
            binding.textSingle.text = "Error"
        }

// ...
//        database = Firebase.database.reference


//        writeNewUser("1", "Murodiljon", "1@mail.com")

    }
//
//    fun writeNewUser(userId: String, name: String, email: String) {
//        val user = User(name, email)
//
//        database.child("users").child(userId).setValue(user)
//        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
//    }

    @RequiresApi(34)
    fun LoadData(number: Any?) {
        // Write a message to the database
        val database = Firebase.database
        val user = User("Murodiljon", "1@mail.com")
        val myRef = database.getReference("100-test")

        myRef.child("Users:").child(number.toString()).setValue(user)
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue()
                Toast.makeText(this@MainActivity, "Read", Toast.LENGTH_SHORT).show()
                binding.text.text = "Value: " + value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.text.text = "Error: $error"

            }

        })
    }
}