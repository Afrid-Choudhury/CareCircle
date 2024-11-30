// DashboardFragment.kt - Defines the dashboard logic and UI - Esther

package com.example.carecircle.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.carecircle.R

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Set up navigation for "Child Login"
        view.findViewById<Button>(R.id.loginAsChildButton).setOnClickListener {
            navigateToChild(view)
        }

        // Set up navigation for "Parent Login"
        view.findViewById<Button>(R.id.loginAsParentButton).setOnClickListener {
            navigateToParent(view)
        }

        return view
    }

    // Method to navigate to MessagesFragment as a child
    private fun navigateToChild(view: View) {
        Log.e("DashboardFragment", "Navigating to MessagesFragment (isParent=false)")
        navigateToFragment(view, isParent = false)
    }

    // Method to navigate to MessagesFragment as a parent
    private fun navigateToParent(view: View) {
        Log.e("DashboardFragment", "Navigating to MessagesFragment (isParent=true)")
        navigateToFragment(view, isParent = true)
    }

    // General method to handle navigation
    private fun navigateToFragment(view: View, isParent: Boolean) {
        try {
            val navController = view.findNavController()
            if (navController == null) {
                Log.e("DashboardFragment", "NavController is null. Navigation cannot proceed.")
                return
            }
            Log.e("DashboardFragment", "Navigating to MessagesFragment (isParent=$isParent)")
            navController.navigate(
                DashboardFragmentDirections.actionDashboardFragmentToMessagesFragment(isParent)
            )
        } catch (e: Exception) {
            Log.e("DashboardFragment", "Navigation failed: ${e.message}")
        }
    }
}
