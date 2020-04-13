package com.demo.app.configs;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {        
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_EXCLUDE_PATTERN = "/policies*";
    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo())
          .securityContexts(Lists.newArrayList(securityContext()))
          .securitySchemes(Lists.newArrayList(apiKey()));                                           
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfo(
    			"Demo Casbin Rest API", 
    			"Basic Implementation of Casbin Api", 
    			"v1.0", 
    			"", 
    			new Contact("Mifta", "www.example.com", "myeaddress@company.com"), 
    			"",
    			"",
    			Collections.emptyList()
    			);
    }
    
    private ApiKey apiKey() {
        return new ApiKey("Token", AUTHORIZATION_HEADER, "Header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(Predicates.not(PathSelectors.regex(DEFAULT_EXCLUDE_PATTERN)))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
            new SecurityReference("Token", authorizationScopes));
    }
}