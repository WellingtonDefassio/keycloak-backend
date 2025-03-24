package br.com.keycloak.backend.backend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MsController {

    @GetMapping("/admin")
    fun adminAccess(): String {
        return "Access Admin"
    }

    @GetMapping("/operation")
    fun adminOperation(): String {
        return "Access Operation"
    }
}