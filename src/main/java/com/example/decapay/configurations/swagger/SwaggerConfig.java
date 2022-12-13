package com.example.decapay.configurations.swagger;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI decaPayAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("DecaPay API ")
                        .description("DecaPay is a personal finance tracker that helps you keep track of your budget.")
                        .version("1.11")

                )
                .components(new Components());

    }
}
