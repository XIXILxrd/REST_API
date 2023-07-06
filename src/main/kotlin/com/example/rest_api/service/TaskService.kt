package com.example.rest_api.service

import com.example.rest_api.model.Task
import com.example.rest_api.repository.TaskRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

private const val DAILY = "daily"
private const val WEEKLY = "weekly"
private const val MONTHLY = "monthly"

@Service
class TaskService(private val repository: TaskRepository) {
    fun getAll(): List<Task> = repository.findAll()
    fun getAll(status: Boolean?): List<Task> = repository.getAllWithStatus(status)

    fun getById(id: Int): Task = repository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun create(task: Task): Task = repository.save(task)

    fun remove(id: Int) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun update(id: Int, task: Task): Task {
        return if (repository.existsById(id)) {
            task.id = id
            repository.save(task)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    fun changeStatus(id: Int): Task {
        return if (repository.existsById(id)) {
            val task = repository.findById(id).get()

            task.status = !task.status
            repository.save(task)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    private fun dailyTasks(status: Boolean?) = repository.tasksForToday(status)

    private fun weeklyTasks(status: Boolean?) = repository.taskForWeek(status)

    private fun monthlyTasks(status: Boolean?) = repository.taskForMonth(status)

    fun viewBy(range: String, status: Boolean?): List<Task> {
        return if (status == null) {
            when (range) {
                DAILY -> dailyTasks(true) + dailyTasks(false)
                WEEKLY -> weeklyTasks(true) + weeklyTasks(false)
                MONTHLY -> monthlyTasks(true) + monthlyTasks(false)
                else -> getAll()
            }
        } else {
            when (range) {
                DAILY -> dailyTasks(status)
                WEEKLY -> weeklyTasks(status)
                MONTHLY -> monthlyTasks(status)
                else -> getAll(status)
            }
        }
    }
}