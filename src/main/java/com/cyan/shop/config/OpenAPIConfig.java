package com.cyan.shop.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API")
                        .description("API for shopping and admin management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Cyan Tran")
                                .email("trannguyendangtam1111@gmail.com")));
    }
}
