package com.example.rest_api.authentication

import com.fasterxml.jackson.annotation.JsonProperty


data class AuthenticationResponse(
    @JsonProperty("access_token")
    private val token: String
) {
    val getToken: String
            get() = token
}