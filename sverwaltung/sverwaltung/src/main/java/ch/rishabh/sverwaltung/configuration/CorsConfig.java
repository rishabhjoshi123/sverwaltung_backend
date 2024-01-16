package ch.rishabh.sverwaltung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class  CorsConfig {

    private static final String FRONT_END_SERVER = "http://localhost:4200/";

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(FRONT_END_SERVER).allowedMethods("*")
                        .allowedHeaders("*").allowCredentials(true);
            }
        };
    }
}
