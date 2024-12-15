package com.example.carecircle

import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * ParentDashboardActivity is the main activity for parents.
 * It displays a welcome message, a list of children, and options for adding children, settings, and logout.
 */
class ParentDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Firebase Authentication reference
    private lateinit var database: DatabaseReference // Firebase Database reference
    private lateinit var childListContainer: LinearLayout // Container for displaying the list of children
    private lateinit var childMessage: TextView // TextView for displaying "No children" message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_dashboard)

        // Initialize Firebase Authentication and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Reference UI elements
        val welcomeText: TextView = findViewById(R.id.welcomeText)
        val addChildButton: Button = findViewById(R.id.addChildButton)
        val settingsButton: Button = findViewById(R.id.settingsButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)
        childListContainer = findViewById(R.id.childListContainer)
        childMessage = findViewById(R.id.childMessage)

        // Fetch user's name and display a personalized welcome message
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

            // Load the list of children for the parent user
            loadChildList(userId)
        }

        // Add child button opens a dialog to add a new child
        addChildButton.setOnClickListener {
            showAddChildDialog(userId)
        }

        // Navigate to settings page
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Handle logout by signing out the user and navigating back to the login screen
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    /**
     * Loads the list of children associated with the parent.
     * If no children exist, it displays a "No children" message.
     */
    private fun loadChildList(userId: String?) {
        if (userId == null) return

        database.child("users").child(userId).child("children")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    childListContainer.removeAllViews()

                    if (snapshot.exists()) {
                        childMessage.visibility = TextView.GONE
                        val children = snapshot.children.toList()

                        for (childSnapshot in children) {
                            val childId = childSnapshot.key
                            val childName = childSnapshot.child("name").value.toString()

                            // Create a card for each child and set a click listener
                            val childCard = createChildCard(childName, "Click for details")
                            childCard.setOnClickListener {
                                val intent = Intent(this@ParentDashboardActivity, ChildDetailsActivity::class.java)
                                intent.putExtra("CHILD_ID", childId)
                                intent.putExtra("CHILD_NAME", childName)
                                startActivity(intent)
                            }
                            childListContainer.addView(childCard)
                        }
                    } else {
                        // Show "No children" message
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

    /**
     * Shows a dialog for adding a new child to the parent's account.
     */
    private fun showAddChildDialog(userId: String?) {
        if (userId == null) return

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_child, null)

        val childNameInput = dialogView.findViewById<EditText>(R.id.childNameInput)
        val childPhoneInput = dialogView.findViewById<EditText>(R.id.childPhoneInput)
        val addChildButton = dialogView.findViewById<Button>(R.id.addChildButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

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
                            addSampleFlaggedMessages(userId, childId, childName)
                            Toast.makeText(this, "Child added successfully!", Toast.LENGTH_SHORT).show()
                            loadChildList(userId)
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

    /**
     * Adds sample flagged messages to the database for testing purposes.
     */
    private fun addSampleFlaggedMessages(parentId: String, childId: String, childName: String) {
        val flaggedMessagesRef = database.child("users").child(parentId).child("flaggedMessages").child(childId)

        val sampleMessages = listOf(
            mapOf(
                "sender" to "+1234567890",
                "date" to "2024-12-01",
                "childName" to childName,
                "childPhone" to "+1987654321",
                "message" to "Let’s meet up somewhere private. You don’t need to tell anyone"
            ),
            mapOf(
                "sender" to "+9876543210",
                "date" to "2024-12-02",
                "childName" to childName,
                "childPhone" to "+1987654321",
                "message" to "Here’s a link to something you’ll love. Click now!"
            ),
            mapOf(
                "sender" to "+5647382910",
                "date" to "2024-12-03",
                "childName" to childName,
                "childPhone" to "+1987654321",
                "message" to "Try vaping; everyone’s doing it, and it’s not harmful."
            )
        )

        for (message in sampleMessages) {
            flaggedMessagesRef.push().setValue(message)
        }
    }

    /**
     * Creates a card view for each child.
     */
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
        }

        val childNameText = TextView(this).apply {
            text = childName
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
        }

        val childAlertText = TextView(this).apply {
            text = alertMessage
        }

        childDetails.addView(childNameText)
        childDetails.addView(childAlertText)
        childCard.addView(initialsCircle)
        childCard.addView(childDetails)

        return childCard
    }
}
