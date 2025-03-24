package br.com.keycloak.backend.backend.utils

import org.springframework.util.LinkedMultiValueMap

class HttpParamsMapBuilder {

    private val params = LinkedMultiValueMap<String, String>()

    fun builder(): HttpParamsMapBuilder {
        return HttpParamsMapBuilder()
    }

    fun withClient(clientId: String): HttpParamsMapBuilder {
        params["client_id"] = clientId
        return this
    }

    fun withClientSecret(secret: String): HttpParamsMapBuilder {
        params["client_secret"] = secret
        return this
    }

    fun withGrantType(grantType: String): HttpParamsMapBuilder {
        params["grant_type"] = grantType
        return this
    }

    fun withUserName(userName: String): HttpParamsMapBuilder {
        params["username"] = userName
        return this
    }

    fun withPassword(password: String): HttpParamsMapBuilder {
        params["password"] = password
        return this
    }

    fun withRefreshToken(refreshToken: String): HttpParamsMapBuilder {
        params["refresh_token"] = refreshToken
        return this
    }

    fun build(): LinkedMultiValueMap<String, String> {
        return params
    }

}