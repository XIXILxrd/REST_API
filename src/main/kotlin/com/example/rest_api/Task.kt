package com.example.rest_api

import java.util.Date

data class Task(val id: String?, val name: String, val deadLine: Long, var status: Boolean)