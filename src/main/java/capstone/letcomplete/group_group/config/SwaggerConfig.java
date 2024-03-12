package capstone.letcomplete.group_group.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition( 
        info = @Info(
                title = "GroupGroup 프로젝트 API 문서",
                description = "GroupGroup 프로젝트에 사용되는 API 문서",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        String jwtSchemeName = "JwtAuthentication";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(
                        jwtSchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")
                );

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}
