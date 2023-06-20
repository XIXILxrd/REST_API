package com.example.rest_api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api")
class TaskController(val service: TaskService) {
    @GetMapping("/date")
    fun getDate(): Long = Date().time
    @GetMapping("/tasks")
    fun getAllTasks() = service.getAll()

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable id: Int) = service.getById(id)

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTask(@RequestBody task: Task): Task = service.create(task)

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Int) = service.remove(id)

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: Task) = service.update(id, task)

    @PutMapping("/tasks/update/{id}")
    fun updateStatus(@PathVariable id: Int) = service.changeStatus(id)
}