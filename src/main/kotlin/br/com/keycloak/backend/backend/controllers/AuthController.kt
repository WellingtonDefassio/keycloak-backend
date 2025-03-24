package br.com.keycloak.backend.backend.controllers

import br.com.keycloak.backend.backend.models.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @PostMapping("/login")
    fun login(@RequestBody user: User) : ResponseEntity<Any> {
        return ResponseEntity.ok(user)
    }

}