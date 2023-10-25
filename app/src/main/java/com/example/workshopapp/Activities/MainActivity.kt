package com.example.workshopapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.workshopapp.Fragments.AvailableWorkshopsFragment
import com.example.workshopapp.Fragments.StudentDashboardFragment
import com.example.workshopapp.R
import com.example.workshopapp.SessionManager

class MainActivity : AppCompatActivity(){
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        sessionManager.setLoggedIn(false)


        val isLoggedIn = checkIfLoggedIn()

//        val initialDestination = R.id.studentDashboardFragment
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

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment is StudentDashboardFragment) {
            currentFragment.navigateToAvailableWorkshopsFragment()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Call the logout function when the app is closed
        val sessionManager = SessionManager(this)
        sessionManager.logout()
    }

    private fun checkIfLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }
}
