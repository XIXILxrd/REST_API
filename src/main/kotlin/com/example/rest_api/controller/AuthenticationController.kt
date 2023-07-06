package com.example.rest_api.controller

import com.example.rest_api.authentication.AuthenticationRequest
import com.example.rest_api.authentication.RegisterRequest
import com.example.rest_api.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class AuthenticationController(private val authenticationService: AuthenticationService) {
    @PostMapping("/signup")
    fun register(@RequestBody request: RegisterRequest): String {
        return authenticationService.register(request).getToken
    }

    @PostMapping("/signin")
    fun authenticate(@RequestBody request: AuthenticationRequest): String {
        return authenticationService.authenticate(request).getToken
    }
}