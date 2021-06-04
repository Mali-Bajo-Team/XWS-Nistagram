package com.xws.users.util.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	// Enable CORS to avoid @CorssOrigin annotation on every controller
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO: Make separated file for this type of config
		registry.addMapping("/**").allowedOrigins("http://localhost:8082", "http://localhost:8083",
				"https://isa-2020-tim29.herokuapp.com/", "http://isa-2020-tim29.herokuapp.com/");
	}
}