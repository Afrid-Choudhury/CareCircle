package com.example.carecircle

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference // Firebase Database reference
    private lateinit var auth: FirebaseAuth // Firebase Authentication reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Authentication and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        Log.e("MainActivity", "App started successfully")

        // Reference the Spinner
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)

        // Define the dropdown options with a placeholder
        val roles = arrayOf("Select an option", "Child", "Parent")

        // Create an ArrayAdapter to bind the options to the Spinner
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout for each item
            roles // Data source
        )

        // Set the dropdown style for the Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Attach the adapter to the Spinner
        roleSpinner.adapter = adapter

        // Reference input fields and button
        val nameField: EditText = findViewById(R.id.nameField)
        val emailField: EditText = findViewById(R.id.emailField)
        val phoneField: EditText = findViewById(R.id.phoneField)
        val passwordField: EditText = findViewById(R.id.passwordField)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        // Handle Create Account Button Click
        createAccountButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val userType = roleSpinner.selectedItem.toString()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || userType == "Select an option") {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData(name, email, phone, password, userType)
            }
        }

        // Handle "Sign in" text
        val signInText: TextView = findViewById(R.id.signInText)
        val spannableString = SpannableString("Already have an account? Sign in")

        // Create a clickable span for "Sign in"
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigate to LoginActivity
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        // Apply blue color to "Sign in"
        val colorSpan = ForegroundColorSpan(Color.BLUE)
        spannableString.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(colorSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the styled text to the TextView
        signInText.text = spannableString
        signInText.setOnClickListener {
            clickableSpan.onClick(it)
        }
    }

    // Save user data to Firebase Authentication and Database
    private fun saveUserData(name: String, email: String, phone: String, password: String, userType: String) {
        // Create a new user in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Create a user object to store additional details in Realtime Database
                        val user = User(name, email, phone, userType)

                        database.child("users").child(userId).setValue(user)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                    // Redirect to login screen
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish() // Close the signup screen
                                } else {
                                    Toast.makeText(this, "Failed to save user data: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                } else {
                    // Show error message if user creation in Firebase Authentication fails
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Data class for storing user details
    data class User(
        val name: String,
        val email: String,
        val phone: String,
        val userType: String
    )
}
