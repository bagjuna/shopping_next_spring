package org.zerock.apiserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer{



  @Override
  public void addCorsMappings(CorsRegistry registry) {

    registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(300)
            .allowCredentials(true) // 자격 증명 허용
            .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
  }



}