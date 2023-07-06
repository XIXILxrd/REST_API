package com.example.rest_api.service

import com.example.rest_api.authentication.AuthenticationRequest
import com.example.rest_api.authentication.AuthenticationResponse
import com.example.rest_api.authentication.RegisterRequest
import com.example.rest_api.config.JwtService
import com.example.rest_api.model.Token
import com.example.rest_api.model.TokenType
import com.example.rest_api.model.User
import com.example.rest_api.repository.TokenRepository
import com.example.rest_api.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = request.role
        )

        userRepository.save(user)

        val jwtToken = jwtService.generateToken(user)
        saveUserToken(user, jwtToken)

        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        val user = userRepository.findByUsername(request.username).orElseThrow()
        val jwtToken = jwtService.generateToken(user)

        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)

        return AuthenticationResponse(jwtToken)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens = user.getId?.let { tokenRepository.findAllValidTokenByUser(it) }

        if (validUserTokens.isNullOrEmpty()) {
            return
        }

        validUserTokens.forEach { token ->
            token.expired = true
        }

        tokenRepository.saveAll(validUserTokens)
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token = Token(
            user = user,
            token = jwtToken,
            tokenType = TokenType.BEARER,
            expired = false
        )

        tokenRepository.save(token)
    }
}