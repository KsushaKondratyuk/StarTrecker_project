package com.grokonez.jwtauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CorsFilter;

//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
//import org.springframework.session.web.http.HttpSessionIdResolver;



@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootJwtAuthenticationApplication {
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
	    return new RestTemplate();
	  }
	

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(SpringBootJwtAuthenticationApplication.class, args);
		final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(CorsFilter.class);
		annotationConfigApplicationContext.refresh();
	}

	@Bean
	CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		CorsFilter bean = new CorsFilter(source);
//		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
	
}

/*@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600 * 24)
class RedisSessionConfig {

	@Bean
    public HttpSessionIdResolver httpSessionStrategy() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

}*/
