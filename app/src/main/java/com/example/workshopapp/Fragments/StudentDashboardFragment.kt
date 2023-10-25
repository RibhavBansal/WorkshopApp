package com.example.workshopapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.R
import com.example.workshopapp.Database.Workshop
import com.example.workshopapp.WorkshopAdapter
import com.example.workshopapp.interfaces.workshop

class StudentDashboardFragment : Fragment(), workshop.WorkshopAdapterListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkshopAdapter
    private lateinit var appliedWorkshops: List<Workshop>  // Store the workshops the student has applied to

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_student_dashboard, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Fetch the list of workshops the student has applied to
        appliedWorkshops = fetchAppliedWorkshopsFromDatabase()
        adapter = WorkshopAdapter(requireContext(),this,appliedWorkshops)
        recyclerView.adapter = adapter

        return rootView
    }

    // Fetch the list of workshops the student has applied to from the database
    private fun fetchAppliedWorkshopsFromDatabase(): List<Workshop> {
        // Implement the database query to fetch applied workshops here
        // You can use the DatabaseHelper for this
        // Ensure that a student cannot register for a workshop twice
        return listOf(
            Workshop(1, "Applied Workshop 1", "Description for Applied Workshop 1"),
            Workshop(3, "Applied Workshop 3", "Description for Applied Workshop 3")
        )
    }

    override fun onLoginOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_loginFragment)
    }

    override fun onRegisterOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_signupFragment)
    }
}
