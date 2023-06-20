package com.example.rest_api

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TaskService(private val repository: TaskRepository) {
    fun getAll(): List<Task> = repository.findAll()

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

//    fun changeStatus(id: Int, task: Task): Task {
//        return if (repository.existsById(id)) {
//            repository.findById(id).get().status = !repository.findById(id).get().status
//            repository.findById(id).get()
//        } else {
//        throw ResponseStatusException(HttpStatus.NOT_FOUND)
//    }
//    }
}