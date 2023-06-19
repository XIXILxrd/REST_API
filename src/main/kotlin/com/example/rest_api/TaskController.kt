package com.example.rest_api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TaskController {
    @GetMapping("/")
    fun index() = listOf(
        Task("1", "Task 1", Date().time, true),
        Task("2", "Task 2", Date().time, false),
        Task("3", "Task 3", Date().time, false),
        Task("4", "Task 4", Date().time, true),
        Task("5", "Task 5", Date().time, false),
        Task("6", "Task 6", Date().time, true),
        Task("7", "Task 7", Date().time, false),
    )
}