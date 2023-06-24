package com.example.rest_api.repository

import com.example.rest_api.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Int> {
    @Query("select * from task where date = current_date and status = ?", nativeQuery = true)
    fun tasksForToday(status: Boolean?): List<Task>

    @Query("select * from task where date <= current_date and  date >= (current_date - interval '7 days') and status = ?", nativeQuery = true)
    fun taskForWeek(status: Boolean?): List<Task>

    @Query("select * from task where date <= current_date and  date >= (current_date - interval '30 days') and status = ?", nativeQuery = true)
    fun taskForMonth(status: Boolean?): List<Task>

    @Query("select * from task where status = ?", nativeQuery = true)
    fun getAllWithStatus(status: Boolean?): List<Task>
}