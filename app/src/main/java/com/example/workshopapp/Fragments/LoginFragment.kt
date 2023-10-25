package com.example.workshopapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workshopapp.Database.DatabaseHelper
import com.example.workshopapp.R

class LoginFragment : Fragment() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        btnLogin = rootView.findViewById(R.id.btnLogin)

        databaseHelper = DatabaseHelper.getInstance(requireContext())
        databaseHelper.insertInitialWorkshops()

        // Set a click listener for the "Sign Up" link
        val tvSignupLink = rootView.findViewById<TextView>(R.id.tvSignupLink)
        tvSignupLink.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signupFragment)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (validateInput(email, password)) {
                val user = databaseHelper.login(email, password)
                if (user != null) {
                    // Successfully logged in, navigate to the student dashboard
                    findNavController().navigate(R.id.action_login_to_studentDashboardFragment)
                } else {
                    // Login failed, show a toast message
                    showToast("Login failed. Invalid credentials.")
                }
            }
        }

        return rootView
    }

    private fun validateInput(email: String, password: String): Boolean {
        // Implement validation logic here
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please fill in all fields.")
            return false
        }
        // You can add more validation rules as needed
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}



