package com.ryoku.dogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ryoku.dogservice.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "Dog Service",
                """
                        Ini merupakan dokumentasi dari Dog Service, saya membagi 2 controller yaitu untuk CRUD ke H2 dan external untuk direct hit ke https://dog.ceo/api dengan rest template.\s

                        Jika terdapat error "consider defining a bean type of mapper", hanya perlu lakukan clean compile maven project lalu run kembali""",
                "1.0",
                "Terms of Service",
                new Contact("Muhammad Nooryoku Rafshanjany", "ryoku.github.io", "nooryoku12331@gmail.com"),
                "Apache License",
                "www.apache.com",
                Collections.emptyList()
        );

        return apiInfo;
    }

}
