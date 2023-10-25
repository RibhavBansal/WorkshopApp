package com.example.workshopapp.interfaces

interface workshop {
    interface WorkshopAdapterListener {
        fun onLoginOptionSelected()
        fun onRegisterOptionSelected()
    }
}