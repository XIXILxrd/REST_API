package com.example.rest_api

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "task")
data class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int,
    val name: String,
    val description: String,
    val deadLine: Date,
    var status: Boolean
)