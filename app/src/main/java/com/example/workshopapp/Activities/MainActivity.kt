package com.example.workshopapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.workshopapp.R

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the user is logged in or not and set the initial destination accordingly
        val isLoggedIn = checkIfLoggedIn()

        val initialDestination = if (isLoggedIn) {
            R.id.studentDashboardFragment // Student Dashboard is the default screen
        } else {
            R.id.availableWorkshopsFragment // LoginFragment as the default screen
        }

        val navController = findNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.startDestination = initialDestination
        navController.graph = navGraph
    }

    private fun checkIfLoggedIn(): Boolean {
        // Implement your logic to check if the user is logged in
        return false // For demonstration purposes, assume the user is logged in
    }
}
