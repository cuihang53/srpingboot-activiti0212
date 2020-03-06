package com.activiti.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
//	private CorsConfiguration buildConfig() {
//	    CorsConfiguration corsConfiguration = new CorsConfiguration();
//	    corsConfiguration.setAllowCredentials(true);
//	    corsConfiguration.addAllowedOrigin("*");
//	    corsConfiguration.addAllowedHeader("*");
//	    corsConfiguration.addAllowedMethod("*");
//	    corsConfiguration.addExposedHeader("Authorization");
//	    List<String> list = new ArrayList<>();
//        list.add("Origin");
//        list.add("Date");
//        corsConfiguration.setExposedHeaders(list);
//	    return corsConfiguration;
//	}
//	 
//	@Bean
//	public CorsFilter corsFilter() {
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", buildConfig());
//	    return new CorsFilter(source);
//	}
	
	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); //允许携带身份令牌
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        List<String> list = new ArrayList<>();
        list.add("Origin");
        list.add("Date");
        corsConfiguration.setExposedHeaders(list);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
	
	
	
	 
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	            .allowedOrigins("*")
	            .allowCredentials(true)
	            .allowedMethods("GET", "POST", "DELETE", "PUT")
	            .maxAge(3600);
	}
}