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

/**
 * MainActivity handles the user registration process. Users can create accounts with different roles (Child or Parent),
 * and their data is stored in Firebase Authentication and Realtime Database.
 */
class MainActivity : AppCompatActivity() {

    // Firebase Authentication and Database references
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Authentication and Database references
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        Log.e("MainActivity", "App started successfully")

        // Reference the Spinner for role selection
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)

        // Define the dropdown options with a placeholder
        val roles = arrayOf("Select an option", "Child", "Parent")

        // Create an ArrayAdapter to bind the options to the Spinner
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            roles
        )

        // Set the dropdown style for the Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        // Reference input fields and button
        val nameField: EditText = findViewById(R.id.nameField)
        val emailField: EditText = findViewById(R.id.emailField)
        val phoneField: EditText = findViewById(R.id.phoneField)
        val passwordField: EditText = findViewById(R.id.passwordField)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        // Handle Create Account button click
        createAccountButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val phone = phoneField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val userType = roleSpinner.selectedItem.toString()

            // Validate input fields
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

        // Apply blue color and make "Sign in" clickable
        val colorSpan = ForegroundColorSpan(Color.BLUE)
        spannableString.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(colorSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signInText.text = spannableString
        signInText.setOnClickListener {
            clickableSpan.onClick(it)
        }
    }

    /**
     * Saves the user data to Firebase Authentication and Realtime Database.
     * @param name The user's name
     * @param email The user's email
     * @param phone The user's phone number
     * @param password The user's password
     * @param userType The user's role (Child or Parent)
     */
    private fun saveUserData(name: String, email: String, phone: String, password: String, userType: String) {
        // Create a new user in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Create a user object to store in Realtime Database
                        val user = User(name, email, phone, userType)

                        database.child("users").child(userId).setValue(user)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                    // Redirect to login screen
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to save user data: ${dbTask.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                } else {
                    // Display error message if user creation fails
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    /**
     * Data class representing a user to be stored in Firebase Realtime Database.
     * @param name The user's name
     * @param email The user's email
     * @param phone The user's phone number
     * @param userType The user's role (Child or Parent)
     */
    data class User(
        val name: String,
        val email: String,
        val phone: String,
        val userType: String
    )
}
