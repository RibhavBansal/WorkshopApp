package com.example.workshopapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshopapp.R
import com.example.workshopapp.Database.Workshop
import com.example.workshopapp.WorkshopAdapter
import com.example.workshopapp.interfaces.workshop

class AvailableWorkshopsFragment : Fragment(), workshop.WorkshopAdapterListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkshopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_available_workshops, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Fetch workshops from the database and set up the adapter
        val workshops = fetchWorkshopsFromDatabase()
        adapter = WorkshopAdapter(requireContext(),this,workshops)
        recyclerView.adapter = adapter

        return rootView
    }

    // Fetch workshops from the SQLite database
    private fun fetchWorkshopsFromDatabase(): List<Workshop> {
        // Implement the database query to fetch workshops here
        // You can use the DatabaseHelper for this
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
