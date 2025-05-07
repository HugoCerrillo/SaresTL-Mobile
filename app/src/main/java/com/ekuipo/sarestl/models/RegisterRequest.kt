package com.ekuipo.sarestl.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val department: String,
    val semester: String,
    val phoneNumber: String,
    val gender: String,
    val dateOfBirth: String,
    val user: String,
    val password: String,
    val userType: String,
    val empresa: String,
)
