package br.com.keycloak.backend.backend.controllers

import br.com.keycloak.backend.backend.models.User
import br.com.keycloak.backend.backend.services.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    val loginService: LoginService
) {

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<String> {
        return loginService.login(user)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestParam("refresh_token") refreshToken: String): ResponseEntity<String> {
        return loginService.refreshToken(refreshToken)
    }

}