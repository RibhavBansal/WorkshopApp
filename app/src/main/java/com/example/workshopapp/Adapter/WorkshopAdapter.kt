package com.example.workshopapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.Database.DatabaseHelper
import com.example.workshopapp.Database.Workshop
import com.example.workshopapp.R
import com.example.workshopapp.SessionManager
import com.example.workshopapp.Interfaces.workshop

class WorkshopAdapter(private val context: Context,
                      private val listener: workshop.WorkshopAdapterListener,
                      private val workshops: List<Workshop>,
                      private val fragmentType: FragmentType
) :
    RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder>() {

    enum class FragmentType {
        AVAILABLE_WORKSHOPS,
        STUDENT_DASHBOARD
    }

    inner class WorkshopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val applyButton: Button? = itemView.findViewById(R.id.btnApply)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val layoutResId = when (fragmentType) {
            FragmentType.AVAILABLE_WORKSHOPS -> R.layout.item_workshop
            FragmentType.STUDENT_DASHBOARD -> R.layout.item_dashboard
        }

        val itemView = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return WorkshopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        val workshop = workshops[position]
        holder.title.text = workshop.title
        holder.description.text = workshop.description

        val sessionManager = SessionManager(context)
        val databaseHelper = DatabaseHelper(context)

        // Handle the "Apply" button click here
        holder.applyButton?.setOnClickListener {
            // Check if the user is logged in (you need to implement a session management mechanism)
            if (sessionManager.isLoggedIn()) {
                // User is logged in, record the application
                recordApplication(workshop.id, sessionManager.getUserId(), databaseHelper)
            } else {
                // User is not logged in, show the dialog with options
                showApplicationDialog(listener)
            }
        }
    }

    private fun recordApplication(workshopId: Int, userId: Int, databaseHelper: DatabaseHelper) {
        // Check if the user has already applied to this workshop
        if (databaseHelper.hasUserApplied(userId, workshopId,databaseHelper.getDatabase())) {
            showToast("You have already applied to this workshop.")
        } else {
            // User hasn't applied yet, record the application
            databaseHelper.recordWorkshopApplication(userId, workshopId,databaseHelper.getDatabase())
            showToast("Application recorded successfully.")
        }
    }

    private fun showApplicationDialog(listener: workshop.WorkshopAdapterListener) {
        val options = arrayOf("Log In", "Register", "Cancel")

        AlertDialog.Builder(context)
            .setTitle("Please Login First!!")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        // Log In option selected
                        listener.onLoginOptionSelected()
                    }
                    1 -> {
                        // Register option selected
                        listener.onRegisterOptionSelected()
                    }
                    2 -> {
                        // Cancel option selected
                    }
                }
            }
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return workshops.size
    }
}
