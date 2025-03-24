package br.com.keycloak.backend.backend.services

import br.com.keycloak.backend.backend.models.User
import org.springframework.http.ResponseEntity

interface LoginService {

    fun login(user: User): ResponseEntity<String>

    fun refreshToken(refreshToken: String): ResponseEntity<String>

}