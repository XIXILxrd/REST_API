package com.example.rest_api.controller

import com.example.rest_api.model.Task
import com.example.rest_api.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class TaskController(private val service: TaskService) {
    @GetMapping("/tasks")
    fun getAllTasks() = service.getAll()

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable id: Int) = service.getById(id)

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTask(@RequestBody task: Task): Task = service.create(task)

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: Int) = service.remove(id)

    @PutMapping("/tasks/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody task: Task) = service.update(id, task)

    @PatchMapping("/tasks/{id}")
    fun updateStatus(@PathVariable id: Int) = service.changeStatus(id)

    @GetMapping("/tasks/view")
    fun viewTasks(
        @RequestParam("range", defaultValue = "") range: String,
        @RequestParam("status", required = false) status: Boolean?
    ): List<Task> {
        return service.viewBy(range, status)
    }
}