package com.example.rest_api.model

import jakarta.persistence.*

@Entity
class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private var id: Int? = null,

    @Column(unique = true)
    val token: String,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val tokenType: TokenType = TokenType.BEARER,

    var expired: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    val user: User
)