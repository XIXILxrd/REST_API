package com.example.rest_api.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column
    val name: String,

    @Column
    val description: String,

    @Column
    val deadLine: Date,

    @Column
    var status: Boolean
)