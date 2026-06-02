package com.nalaka.goviya.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenApiConfig {

    @Value("${swagger.server-url}")
    private String swaggerServerUrl;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI openAPI() {
        Server serverSettings = new Server();
        serverSettings.setUrl(swaggerServerUrl + contextPath);
        return new OpenAPI()
                .info(new Info()
                        .title("Ratings and Reviews Service")
                        .description("This API manages ratings and reviews for Goviya.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nalaka Dinesh")
                                .url("https://www.nalaka.online//")
                                .email("nalakadineshx@gmail.com"))).servers(Collections.singletonList(serverSettings));
    }
}
