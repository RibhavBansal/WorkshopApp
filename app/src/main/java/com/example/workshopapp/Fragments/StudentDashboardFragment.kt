package com.example.workshopapp.Fragments

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.Database.DatabaseHelper
import com.example.workshopapp.R
import com.example.workshopapp.Database.Workshop
import com.example.workshopapp.SessionManager
import com.example.workshopapp.WorkshopAdapter
import com.example.workshopapp.interfaces.workshop

class StudentDashboardFragment : Fragment(), workshop.WorkshopAdapterListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkshopAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private val appliedWorkshops = ArrayList<Workshop>() // Store the workshops the student has applied to

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_student_dashboard, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        databaseHelper = DatabaseHelper(requireContext())

        adapter = WorkshopAdapter(requireContext(),this,appliedWorkshops, WorkshopAdapter.FragmentType.STUDENT_DASHBOARD)
        recyclerView.adapter = adapter


        val context = requireContext()
        val dbHelper = DatabaseHelper.getInstance(context)
        val sessionManager = SessionManager(context)
        val userId = sessionManager.getUserId()


        val db = dbHelper.getDatabase()
        appliedWorkshops.clear()
        appliedWorkshops.addAll(dbHelper.fetchAppliedWorkshopsFromDatabase(userId, db))
        adapter.notifyDataSetChanged()

        return rootView
    }


    override fun onLoginOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_loginFragment)
    }

    override fun onRegisterOptionSelected() {
        findNavController().navigate(R.id.action_availableWorkshopsFragment_to_signupFragment)
    }

    fun navigateToAvailableWorkshopsFragment() {
        // Navigate back to the Available Workshops fragment
        val availableWorkshopsFragment = AvailableWorkshopsFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, availableWorkshopsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

