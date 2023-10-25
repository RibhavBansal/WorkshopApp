package com.example.workshopapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshopapp.R
import com.example.workshopapp.Database.Workshop
import com.example.workshopapp.SessionManager
import com.example.workshopapp.Adapter.WorkshopAdapter
import com.example.workshopapp.Interfaces.workshop

class AvailableWorkshopsFragment : Fragment(), workshop.WorkshopAdapterListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkshopAdapter
    private lateinit var btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_available_workshops, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        btn = rootView.findViewById(R.id.btnNavigateToStudent)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val sessionManager = SessionManager(requireContext())

        // Fetch workshops from the database and set up the adapter
        val workshops = fetchWorkshopsFromDatabase()
        adapter = WorkshopAdapter(requireContext(),this,workshops, WorkshopAdapter.FragmentType.AVAILABLE_WORKSHOPS)
        recyclerView.adapter = adapter

        btn.setOnClickListener{
            if (sessionManager.isLoggedIn())
            findNavController().navigate(R.id.studentDashboardFragment)
            else{
                showApplicationDialog()
            }
        }

        return rootView
    }

    private fun showApplicationDialog() {
        val options = arrayOf("Log In", "Register", "Cancel")

        AlertDialog.Builder(requireContext())
            .setTitle("Please Login First!!")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        // Log In option selected
                        onLoginOptionSelected()
                    }
                    1 -> {
                        // Register option selected
                        onRegisterOptionSelected()
                    }
                    2 -> {
                        // Cancel option selected
                    }
                }
            }
            .show()
    }

    private fun fetchWorkshopsFromDatabase(): List<Workshop> {
        return listOf(
            Workshop(1, "Workshop 1", "Description for Workshop 1"),
            Workshop(2, "Workshop 2", "Description for Workshop 2"),
            Workshop(3, "Workshop 3", "Description for Workshop 3")
        )
    }

    override fun onLoginOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_loginFragment)
    }

    override fun onRegisterOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_signupFragment)
    }
}
