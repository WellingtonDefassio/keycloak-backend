package br.com.keycloak.backend.backend.components

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class HttpComponent {

    @Bean
    fun httpHeaders(): HttpHeaders {
        return HttpHeaders()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}