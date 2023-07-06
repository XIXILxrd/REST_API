package com.example.rest_api.repository

import com.example.rest_api.model.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TokenRepository: JpaRepository<Token, Int> {
    @Query("select * from token join user_auth on user_auth.user_id = token.id_user where user_auth.user_id = ? and token.expired = false", nativeQuery = true)
    fun findAllValidTokenByUser(id: Int): List<Token>

    @Query("select * from token where token.token = ?", nativeQuery = true)
    fun findByToken(token: String): Optional<Token>
}