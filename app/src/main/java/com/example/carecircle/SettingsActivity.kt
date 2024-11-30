package com.example.carecircle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Reference UI elements
        val profileInfo: TextView = findViewById(R.id.profileInfo)
        val emailNotificationSwitch: Switch = findViewById(R.id.emailNotificationSwitch)
        val pushNotificationSwitch: Switch = findViewById(R.id.pushNotificationSwitch)
        val twoFactorSwitch: Switch = findViewById(R.id.twoFactorSwitch)
        val editProfileButton: TextView = findViewById(R.id.editProfileButton)
        val changePasswordButton: TextView = findViewById(R.id.changePasswordButton)

        // Fetch and display user information
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("name").value.toString()
                    val email = snapshot.child("email").value.toString()
                    val phone = snapshot.child("phone").value.toString()
                    profileInfo.text = "Name: $name\nEmail: $email\nPhone: $phone"
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Error fetching profile: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        // Handle Email Notification Toggle
        emailNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Email notifications enabled" else "Email notifications disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Handle Push Notification Toggle
        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Push notifications enabled" else "Push notifications disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Handle Two-Factor Authentication Toggle
        twoFactorSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Two-Factor Authentication enabled" else "Two-Factor Authentication disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Handle Edit Profile Button Click
        editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }

        // Handle Change Password Button Click
        changePasswordButton.setOnClickListener {
            showChangePasswordDialog()
        }
    }

    private fun showEditProfileDialog() {
        // Inflate the dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)

        val editName = dialogView.findViewById<EditText>(R.id.editName)
        val editEmail = dialogView.findViewById<EditText>(R.id.editEmail)
        val editPhone = dialogView.findViewById<EditText>(R.id.editPhone)
        val saveChangesButton = dialogView.findViewById<Button>(R.id.saveChangesButton)

        // Pre-fill the fields with the current user data
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    editName.setText(snapshot.child("name").value.toString())
                    editEmail.setText(snapshot.child("email").value.toString())
                    editPhone.setText(snapshot.child("phone").value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@SettingsActivity,
                        "Error fetching data: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        // Build the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Handle Save Changes Button
        saveChangesButton.setOnClickListener {
            val newName = editName.text.toString().trim()
            val newEmail = editEmail.text.toString().trim()
            val newPhone = editPhone.text.toString().trim()

            if (newName.isNotEmpty() && newEmail.isNotEmpty() && newPhone.isNotEmpty()) {
                // Update Firebase Realtime Database
                if (userId != null) {
                    val updates = mapOf(
                        "name" to newName,
                        "email" to newEmail,
                        "phone" to newPhone
                    )
                    database.child("users").child(userId).updateChildren(updates)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // Update Firebase Authentication (for email update)
                                auth.currentUser?.updateEmail(newEmail)?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    } else {
                                        Toast.makeText(this, "Failed to update email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Failed to update profile.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null)
        val alertDialog = AlertDialog.Builder(this).setView(dialogView).create()

        val oldPasswordField = dialogView.findViewById<EditText>(R.id.oldPasswordField)
        val newPasswordField = dialogView.findViewById<EditText>(R.id.newPasswordField)
        val resetPasswordButton = dialogView.findViewById<Button>(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val oldPassword = oldPasswordField.text.toString().trim()
            val newPassword = newPasswordField.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updatePassword(oldPassword, newPassword, alertDialog)
        }

        alertDialog.show()
    }

    private fun updatePassword(oldPassword: String, newPassword: String, alertDialog: AlertDialog) {
        val user = auth.currentUser
        val email = user?.email

        if (email != null) {
            val credential = EmailAuthProvider.getCredential(email, oldPassword)

            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                                    alertDialog.dismiss()
                                } else {
                                    Toast.makeText(this, "Failed to update password: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
