package com.example.rest_api.controller

import com.example.rest_api.TaskService
import com.example.rest_api.model.Task
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

private const val DAILY = "daily"
private const val WEEKLY = "weekly"
private const val MONTHLY = "monthly"

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

    @GetMapping("/tasks/view")
    fun viewTasks(@RequestParam("range", defaultValue = "") range: String,
                  @RequestParam("status", required = false) status: Boolean?
    ): List<Task> {
        return if (status == null) {
            when (range) {
                DAILY -> service.dailyTasks(true) + service.dailyTasks(false)
                WEEKLY -> service.weeklyTasks(true) + service.weeklyTasks(false)
                MONTHLY -> service.monthlyTasks(true) + service.monthlyTasks(false)
                else -> service.getAll()
            }
        } else {
            when (range) {
                DAILY -> service.dailyTasks(status)
                WEEKLY -> service.weeklyTasks(status)
                MONTHLY -> service.monthlyTasks(status)
                else -> service.getAll(status)
            }
        }
    }
}