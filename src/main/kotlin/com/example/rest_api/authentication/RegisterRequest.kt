package com.example.rest_api.authentication

import com.example.rest_api.model.Role


data class RegisterRequest(
    val username: String,
    val password: String,
    val role: Role
)