package br.com.keycloak.backend.backend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    @Value("\${keycloak.auth-server.url}")
    val keycloakServerUrl: String
) {


    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("login", "refresh").permitAll()
                it.requestMatchers("admin").hasAnyAuthority("ADMIN_READ")
            }
            .oauth2ResourceServer {ouath2 -> ouath2.jwt{jwt -> jwt.decoder(jwtDecoder())}}
            .build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder
            .withJwkSetUri("${keycloakServerUrl}/protocol/openid-connect/certs")
            .build()
    }

    @Bean
    fun jwtAuthenticationConverterForKeycloak(): JwtAuthenticationConverter {
        try {
            val converter = Converter<Jwt, Collection<GrantedAuthority>> { jwt ->

                val resourceAccess = jwt.getClaim<Map<String, Any>>("realm_access")
                println("Resource Access: $resourceAccess") // Debug
                val roles = resourceAccess["roles"] as Collection<String>
                println("Roles extraídos: $roles") // Debug
                roles.stream().map {
                    SimpleGrantedAuthority(it)
                }.toList()
            }

            val jwtAuthenticationConverter = JwtAuthenticationConverter()
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter)
            return jwtAuthenticationConverter
        } catch (e: Error) {
            println(e.message)
            e.printStackTrace()
        }
        return JwtAuthenticationConverter()
    }

}