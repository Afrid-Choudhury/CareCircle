package com.example.carecircle

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// This activity manages the Child Dashboard, displaying a welcome message,
// monitoring options, and navigation features such as Settings and Logout.

class ChildDashboardActivity : AppCompatActivity() {

    // Declare UI elements
    private lateinit var monitoringStatusText: TextView
    private lateinit var monitoringSwitch: Switch
    private lateinit var enablePermissionsSwitch: Switch
    private lateinit var settingsButton: TextView
    private lateinit var logoutButton: TextView
    private lateinit var welcomeMessage: TextView

    // Firebase authentication instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_dashboard)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        // Reference UI elements from the XML layout
        welcomeMessage = findViewById(R.id.welcomeMessage)
        monitoringStatusText = findViewById(R.id.monitoringStatusText)
        monitoringSwitch = findViewById(R.id.monitoringSwitch)
        enablePermissionsSwitch = findViewById(R.id.enablePermissionsSwitch)
        settingsButton = findViewById(R.id.settingsButton)
        logoutButton = findViewById(R.id.logoutButton)

        // Fetch the child's name from the Firebase database and display it
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance().reference
            database.child("users").child(userId).child("name")
                .get() // Asynchronously fetch the name
                .addOnSuccessListener { snapshot ->
                    val childName = snapshot.getValue(String::class.java) ?: "Child"
                    welcomeMessage.text = "Welcome to the Child Dashboard, $childName"
                }
                .addOnFailureListener { error ->
                    // Show an error message if the data fetch fails
                    Toast.makeText(
                        this@ChildDashboardActivity,
                        "Error fetching child name: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        // Handle Monitoring Switch state changes
        monitoringSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                monitoringStatusText.text = "Monitoring is currently active"
                monitoringStatusText.setBackgroundResource(R.drawable.active_status_background)
            } else {
                monitoringStatusText.text = "Monitoring is currently inactive"
                monitoringStatusText.setBackgroundResource(R.drawable.inactive_status_background)
            }
        }

        // Handle Enable Permissions Switch state changes
        enablePermissionsSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                Toast.makeText(this, "Permissions enabled", Toast.LENGTH_SHORT).show()
                // Automatically activate monitoring when permissions are enabled
                monitoringSwitch.isChecked = true
            } else {
                Toast.makeText(this, "Permissions disabled", Toast.LENGTH_SHORT).show()
                // Automatically deactivate monitoring when permissions are disabled
                monitoringSwitch.isChecked = false
            }
        }

        // Navigate to the Settings page when the Settings button is clicked
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent) // Start the SettingsActivity
        }

        // Log out the user and redirect them to the Login screen
        logoutButton.setOnClickListener {
            auth.signOut() // Sign out from Firebase authentication
            val intent = Intent(this, LoginActivity::class.java) // Redirect to Login screen
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Close the current activity
        }
    }
}
