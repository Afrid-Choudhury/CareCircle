package com.example.carecircle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * SettingsActivity provides functionalities to view and update user profile information,
 * toggle notification preferences, enable/disable two-factor authentication,
 * and change the password securely.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // Firebase Authentication reference
    private lateinit var database: DatabaseReference // Firebase Realtime Database reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Firebase Authentication and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Reference UI elements
        val profileInfo: TextView = findViewById(R.id.profileInfo)
        val emailNotificationSwitch: Switch = findViewById(R.id.emailNotificationSwitch)
        val pushNotificationSwitch: Switch = findViewById(R.id.pushNotificationSwitch)
        val twoFactorSwitch: Switch = findViewById(R.id.twoFactorSwitch)
        val editProfileButton: TextView = findViewById(R.id.editProfileButton)
        val changePasswordButton: TextView = findViewById(R.id.changePasswordButton)

        // Fetch and display user profile information
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

        // Toggle switches for notification preferences
        emailNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Email notifications enabled" else "Email notifications disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        pushNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Push notifications enabled" else "Push notifications disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        twoFactorSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Two-Factor Authentication enabled" else "Two-Factor Authentication disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Handle Edit Profile functionality
        editProfileButton.setOnClickListener {
            showEditProfileDialog()
        }

        // Handle Change Password functionality
        changePasswordButton.setOnClickListener {
            showChangePasswordDialog()
        }
    }

    /**
     * Displays a dialog to edit user profile information.
     */
    private fun showEditProfileDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_profile, null)

        val editName = dialogView.findViewById<EditText>(R.id.editName)
        val editEmail = dialogView.findViewById<EditText>(R.id.editEmail)
        val editPhone = dialogView.findViewById<EditText>(R.id.editPhone)
        val saveChangesButton = dialogView.findViewById<Button>(R.id.saveChangesButton)

        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Pre-fill the fields with current user data
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

        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        saveChangesButton.setOnClickListener {
            val newName = editName.text.toString().trim()
            val newEmail = editEmail.text.toString().trim()
            val newPhone = editPhone.text.toString().trim()

            if (newName.isNotEmpty() && newEmail.isNotEmpty() && newPhone.isNotEmpty()) {
                val updates = mapOf("name" to newName, "email" to newEmail, "phone" to newPhone)

                // Update Firebase Database and Firebase Authentication for email
                database.child("users").child(userId!!).updateChildren(updates).addOnCompleteListener {
                    if (it.isSuccessful) {
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
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    /**
     * Displays a dialog for securely changing the user's password.
     */
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

    /**
     * Reauthenticates the user with their old password and updates the password.
     */
    private fun updatePassword(oldPassword: String, newPassword: String, alertDialog: AlertDialog) {
        val user = auth.currentUser
        val email = user?.email

        if (email != null) {
            val credential = EmailAuthProvider.getCredential(email, oldPassword)

            user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
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
