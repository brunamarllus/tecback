package br.uniesp.si.techback.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techbackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IESPFLIX API")
                        .description("API REST de catálogo de streaming (conteúdos, "
                                + "usuários, assinaturas, favoritos, métodos de pagamento e planos).")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Techback - Uniesp")));
    }
}
