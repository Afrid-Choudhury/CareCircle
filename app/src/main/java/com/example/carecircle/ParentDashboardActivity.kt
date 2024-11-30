package com.example.carecircle

import android.graphics.Typeface
import android.view.Gravity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ParentDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var childListContainer: LinearLayout
    private lateinit var childMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_dashboard)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Reference UI elements
        val welcomeText: TextView = findViewById(R.id.welcomeText)
        val addChildButton: Button = findViewById(R.id.addChildButton)
        val settingsButton: Button = findViewById(R.id.settingsButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)
        childListContainer = findViewById(R.id.childListContainer)
        childMessage = findViewById(R.id.childMessage)

        // Fetch user's name and update the welcome text
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).child("name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userName = snapshot.getValue(String::class.java) ?: "User"
                        welcomeText.text = "Welcome to the Parent Dashboard, $userName"
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@ParentDashboardActivity,
                            "Error fetching user name: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            // Load child list for the user
            loadChildList(userId)
        }

        // Handle Add Child button click
        addChildButton.setOnClickListener {
            showAddChildDialog(userId)
        }

        // Handle Settings button click
        settingsButton.setOnClickListener {
            // Navigate to SettingsActivity
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Handle Logout button click
        logoutButton.setOnClickListener {
            auth.signOut() // Sign out from Firebase
            val intent = Intent(this, LoginActivity::class.java) // Redirect to LoginActivity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Close the current activity
        }
    }

    private fun loadChildList(userId: String?) {
        if (userId == null) return

        database.child("users").child(userId).child("children")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    childListContainer.removeAllViews()

                    if (snapshot.exists()) {
                        childMessage.visibility = TextView.GONE
                        val children = snapshot.children.toList()
                        val maxVisibleChildren = 4

                        // Display the first 4 children or all children if count <= 4
                        for ((index, childSnapshot) in children.withIndex()) {
                            if (index >= maxVisibleChildren) break

                            val childName = childSnapshot.child("name").value.toString()
                            val childPhone = childSnapshot.child("phone").value.toString()

                            val childCard = createChildCard(childName, "No new alerts")
                            childListContainer.addView(childCard)
                        }

                        // Add "See More" link if there are more than 4 children
                        if (children.size > maxVisibleChildren) {
                            val seeMoreButton = TextView(this@ParentDashboardActivity).apply {
                                text = "See More"
                                textSize = 16f
                                setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                                setTypeface(null, Typeface.BOLD)
                                gravity = Gravity.CENTER
                                setPadding(16, 16, 16, 16)
                                setOnClickListener {
                                    // Clear the container and show all children
                                    childListContainer.removeAllViews()
                                    for (childSnapshot in children) {
                                        val childName = childSnapshot.child("name").value.toString()
                                        val childPhone = childSnapshot.child("phone").value.toString()

                                        val childCard = createChildCard(childName, "No new alerts")
                                        childListContainer.addView(childCard)
                                    }
                                }
                            }
                            childListContainer.addView(seeMoreButton)
                        }
                    } else {
                        // Show the "No child to show yet!" message
                        childMessage.visibility = TextView.VISIBLE
                        childMessage.text = "No child to show yet!"
                        childListContainer.addView(childMessage)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ParentDashboardActivity,
                        "Error loading children: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun showAddChildDialog(userId: String?) {
        if (userId == null) return

        // Inflate the dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_child, null)

        val childNameInput = dialogView.findViewById<EditText>(R.id.childNameInput)
        val childPhoneInput = dialogView.findViewById<EditText>(R.id.childPhoneInput)
        val addChildButton = dialogView.findViewById<Button>(R.id.addChildButton)

        // Build the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Handle Add Child Button Click
        addChildButton.setOnClickListener {
            val childName = childNameInput.text.toString().trim()
            val childPhone = childPhoneInput.text.toString().trim()

            if (childName.isNotEmpty() && childPhone.isNotEmpty()) {
                val childId = database.child("users").child(userId).child("children").push().key ?: return@setOnClickListener
                val child = mapOf(
                    "id" to childId,
                    "name" to childName,
                    "phone" to childPhone
                )

                database.child("users").child(userId).child("children").child(childId)
                    .setValue(child)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Child added successfully!", Toast.LENGTH_SHORT).show()
                            loadChildList(userId) // Refresh the child list
                            dialog.dismiss()
                        } else {
                            Toast.makeText(this, "Failed to add child: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun createChildCard(childName: String, alertMessage: String): LinearLayout {
        val childCard = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setBackgroundResource(R.drawable.child_card_background)
            setPadding(16, 16, 16, 16)
            elevation = 4f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
        }

        val initialsCircle = TextView(this).apply {
            text = childName.first().uppercaseChar().toString()
            setBackgroundResource(R.drawable.child_circle_background)
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.white))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(100, 100).apply {
                marginEnd = 16
            }
        }

        val childDetails = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val childNameText = TextView(this).apply {
            text = childName
            textSize = 18f
            setTextColor(resources.getColor(android.R.color.black))
            setTypeface(null, Typeface.BOLD)
        }

        val childAlertText = TextView(this).apply {
            text = alertMessage
            textSize = 14f
            setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        childDetails.addView(childNameText)
        childDetails.addView(childAlertText)
        childCard.addView(initialsCircle)
        childCard.addView(childDetails)

        return childCard
    }
}
