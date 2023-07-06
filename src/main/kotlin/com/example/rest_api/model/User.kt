package com.example.rest_api.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user_auth")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private var id: Int? = null,

    @Column(unique = true)
    private var username: String,

    @Column
    private var password: String,

    @Column
    @Enumerated(EnumType.STRING)
    private var role: Role,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    open var tokens: MutableList<Token> = mutableListOf()
) : UserDetails {

    val getId: Int?
        get() = id
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return role.getAuthorities()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}