package org.zerock.apiserver.global.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. 이미지가 실제로 저장된 서버의 물리적 경로입니다.
    // (주의) Windows는 "file:///" 3개, Linux/Mac은 "file:/" 1개 입니다.
    // (예) Windows: "file:///C:/Users/myuser/uploads/"
    // (예) Linux: "file:/home/myuser/uploads/"
    private String resourcePath = "file:/Users/bagjun-a/temp/images/"; // ⬅️ 본인 환경에 맞게 수정

    // 2. 브라우저에서 요청할 URL 경로입니다. (SecurityConfig와 일치)
    private String requestPath = "/api/images/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(requestPath) // /api/images/** 요청이 오면
                .addResourceLocations(resourcePath); // C:/uploads/ 폴더에서 파일을 찾아라
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
            .allowedOrigins("http://localhost:3000") // 프론트엔드 주소 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
