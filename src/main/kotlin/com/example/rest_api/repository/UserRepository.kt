package com.example.rest_api.repository

import com.example.rest_api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}