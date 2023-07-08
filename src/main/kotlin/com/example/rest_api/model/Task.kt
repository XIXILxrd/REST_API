package com.example.rest_api.model

import jakarta.persistence.*
import java.sql.Date

@Entity
data class Task(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Int? = null,

        @Column
        val name: String,

        @Column
        val description: String,

        @Column
        var date: Date,

        @Column
        var status: Boolean
)