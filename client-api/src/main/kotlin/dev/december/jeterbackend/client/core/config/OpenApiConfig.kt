package dev.december.jeterbackend.client.core.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.SecurityScheme


@SecurityScheme(
  name = "security_auth",
  type = SecuritySchemeType.OAUTH2,
  flows = OAuthFlows(
    clientCredentials = OAuthFlow(tokenUrl = "/client-api/auth")
  )
)
class OpenApiConfig