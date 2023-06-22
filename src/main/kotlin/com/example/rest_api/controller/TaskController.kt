package com.example.rest_api.controller

import com.example.rest_api.TaskService
import com.example.rest_api.model.Task
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
class TaskController(private val service: TaskService) {
    @GetMapping("/tasks")
    fun getAllTasks() = service.getAll()

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable id: Int) = service.getById(id)

    @PostMapping("/tasks/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTask(@RequestBody task: Task): Task = service.create(task)

    @DeleteMapping("/tasks/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Int) = service.remove(id)

    @PutMapping("/tasks/update/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: Task) = service.update(id, task)

    @PatchMapping("/tasks/change_status/{id}")
    fun updateStatus(@PathVariable id: Int) = service.changeStatus(id)
}