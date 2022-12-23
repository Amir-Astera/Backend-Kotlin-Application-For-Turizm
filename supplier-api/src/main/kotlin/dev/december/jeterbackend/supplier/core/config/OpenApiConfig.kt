package dev.december.jeterbackend.supplier.core.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.SecurityScheme


@SecurityScheme(
  name = "security_auth",
  type = SecuritySchemeType.OAUTH2,
  flows = OAuthFlows(
    clientCredentials = OAuthFlow(tokenUrl = "/supplier-api/auth")
  )
)
class OpenApiConfig