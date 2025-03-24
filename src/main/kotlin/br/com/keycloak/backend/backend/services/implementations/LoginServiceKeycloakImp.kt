package br.com.keycloak.backend.backend.services.implementations

import br.com.keycloak.backend.backend.components.HttpComponent
import br.com.keycloak.backend.backend.models.User
import br.com.keycloak.backend.backend.services.LoginService
import br.com.keycloak.backend.backend.utils.HttpParamsMapBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class LoginServiceKeycloakImp(
    val httpComponent: HttpComponent,
    @Value("\${keycloak.auth-server.url}")
    val keycloakServerUrl: String,
    @Value("\${keycloak.auth-server.realm}")
    val realm: String,
    @Value("\${keycloak.auth-server.resource}")
    val clientId: String,
    @Value("\${keycloak.auth-server.secret}")
    val clientSecret: String,
    @Value("\${keycloak.auth-server.grant_type}")
    val grantType: String
) : LoginService {

    override fun login(user: User): ResponseEntity<String> {
        val headers = httpComponent.httpHeaders()
        headers.contentType = APPLICATION_FORM_URLENCODED

        val body = HttpParamsMapBuilder().builder()
            .withClient(clientId)
            .withGrantType(grantType)
            .withUserName(user.username)
            .withPassword(user.password)
            .withClientSecret(clientSecret)
            .build()

        val request = HttpEntity(body, headers)

        try {
            val responseEntity = httpComponent.restTemplate().postForEntity(
                "${keycloakServerUrl}/protocol/openid-connect/token",
                request,
                String::class.java
            )
            return ResponseEntity.ok(responseEntity.body)
        } catch (e: HttpClientErrorException) {
            return ResponseEntity.status(e.statusCode).body(e.message)
        }
    }

    override fun refreshToken(refreshToken: String): ResponseEntity<String> {
        val headers = httpComponent.httpHeaders()
        headers.contentType = APPLICATION_FORM_URLENCODED

        val body = HttpParamsMapBuilder().builder()
            .withClient(clientId)
            .withClientSecret(clientSecret)
            .withGrantType("refresh_token")
            .withRefreshToken(refreshToken)
            .build()

        val request = HttpEntity(body, headers)

        try {
            val responseEntity = httpComponent.restTemplate().postForEntity(
                "${keycloakServerUrl}/protocol/openid-connect/token",
                request,
                String::class.java
            )
            return ResponseEntity.ok(responseEntity.body)
        } catch (e: HttpClientErrorException) {
            return ResponseEntity.status(e.statusCode).body(e.message)
        }
    }
}