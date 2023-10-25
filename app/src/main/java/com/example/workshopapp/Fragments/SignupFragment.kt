package com.example.workshopapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.workshopapp.Database.DatabaseHelper
import com.example.workshopapp.Database.User
import com.example.workshopapp.R

class SignupFragment : Fragment() {
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)

        etFullName = rootView.findViewById(R.id.etFullName)
        etEmail = rootView.findViewById(R.id.etEmail)
        etPassword = rootView.findViewById(R.id.etPassword)
        btnSignup = rootView.findViewById(R.id.btnSignup)

        databaseHelper = DatabaseHelper.getInstance(requireContext())
        databaseHelper.insertInitialWorkshops()

        // Set a click listener for the "Log In" link
        val tvLoginLink = rootView.findViewById<TextView>(R.id.tvLoginLink)
        tvLoginLink.setOnClickListener {
            findNavController().navigate(R.id.action_signup_to_loginFragment)
        }

        btnSignup.setOnClickListener {
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val user = User(0, fullName, email, password)
            databaseHelper.registerUser(user)

            // After successful registration, navigate to the student dashboard
            findNavController().navigate(R.id.action_signup_to_studentDashboardFragment)
        }

        return rootView
    }
}
