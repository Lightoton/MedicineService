package com.rangers.medicineservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@OpenAPIDefinition(
        info = @Info(
                title = "Medicine service",
                description = "This is a telegram bot that provides medical services. " +
                        "It consists of the following modules: clinic, doctor and pharmacy, openai-chat bot, telegram bot." +
                        "Data consist of doctors, medicines, orders, orderDetails, cartItems, " +
                        "pharmacies, prescriptions, schedules, users.</br>" +
                        "Developed by: </br>" +
                        "<a href=\" https://github.com/Alexf17\">Oleksii Chilibiiskyi</a></br>" +
                        "<a href=\" https://www.linkedin.com/in/maksym-bondarenko-8a6ba0280/?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app\">Maksym Bondarenko</a></br>" +
                        "<a href=\" https://www.linkedin.com/in/oleksandr-harbuz-1b9b41300\">Oleksandr Harbuz</a></br>" +
                        "<a href=\"https://www.linkedin.com/feed/?trk=guest_homepage-basic_google-one-tap-submit\">Volha Zadziarkouskaya</a> </br>" +
                        "<a href=\" https://github.com/Lightoton\">Viktor Bulatov</a></br>",
                version = "1.0.0"
        )
)
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.packageName:com.rangers.medicineservice}")
    private String PACKAGE_NAME;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build();
    }
}
