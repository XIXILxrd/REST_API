package com.example.rest_api.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors


enum class Role(private val permissions: Set<Permission>) {
    ROLE_USER(
        setOf(Permission.USER_READ)
    ),
    ROLE_ADMIN(
        setOf(
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_CREATE,
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_CREATE
        )
    ),
    ROLE_MANAGER(
        setOf(
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
        )
    );

    fun getAuthorities(): MutableList<SimpleGrantedAuthority> {
        val authorities = permissions
            .stream()
            .map { SimpleGrantedAuthority(it.permission)}
            .collect(Collectors.toList())

        authorities.add(SimpleGrantedAuthority("ROLE_" + this.name))

        return authorities
    }
}