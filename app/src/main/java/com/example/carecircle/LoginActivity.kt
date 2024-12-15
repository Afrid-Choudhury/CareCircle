package com.example.carecircle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * LoginActivity handles the login functionality for the app, allowing users to log in and redirecting them
 * to the appropriate dashboard (Parent or Child) based on their user type.
 */
class LoginActivity : AppCompatActivity() {

    // Firebase authentication and database references
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Reference UI elements
        val emailField: EditText = findViewById(R.id.emailField) // Field for the user's email
        val passwordField: EditText = findViewById(R.id.passwordField) // Field for the user's password
        val loginButton: Button = findViewById(R.id.loginButton) // Button to trigger the login process
        val forgotPassword: TextView = findViewById(R.id.forgotPassword) // TextView for forgot password
        val signUp: TextView = findViewById(R.id.signUp) // TextView to navigate to the sign-up screen

        // Handle Login Button Click
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate email and password fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password) // Call the login function
            }
        }

        // Handle Forgot Password Click
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
            // Future: Implement forgot password functionality here
        }

        // Handle Sign Up Click
        signUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Redirect to the sign-up screen
            startActivity(intent)
        }
    }

    /**
     * Logs in the user using Firebase Authentication.
     * @param email The email address entered by the user.
     * @param password The password entered by the user.
     */
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful, retrieve user ID
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        getUserTypeAndRedirect(userId) // Fetch user type and redirect accordingly
                    }
                } else {
                    // Login failed, display an error message
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Retrieves the user type (Parent or Child) from Firebase Realtime Database and redirects the user
     * to the appropriate dashboard.
     * @param userId The unique Firebase user ID of the logged-in user.
     */
    private fun getUserTypeAndRedirect(userId: String) {
        // Fetch user details from the "users" node in Firebase
        database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Extract userType from the database
                    val userType = snapshot.child("userType").value.toString()
                    if (userType == "Child") {
                        // Redirect to the Child Dashboard
                        val intent = Intent(this@LoginActivity, ChildDashboardActivity::class.java)
                        startActivity(intent)
                    } else if (userType == "Parent") {
                        // Redirect to the Parent Dashboard
                        val intent = Intent(this@LoginActivity, ParentDashboardActivity::class.java)
                        startActivity(intent)
                    }
                    finish() // Close the login activity to prevent back navigation
                } else {
                    // If the userType is not found in the database
                    Toast.makeText(this@LoginActivity, "User type not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors that occur while reading data from the database
                Toast.makeText(this@LoginActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
