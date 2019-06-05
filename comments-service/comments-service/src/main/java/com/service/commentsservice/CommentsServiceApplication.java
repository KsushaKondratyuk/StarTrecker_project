package com.service.commentsservice;

import com.duplicate.microservices.hazelcast.cache.HazelcastClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Import(HazelcastClientConfig.class)
@SpringBootApplication
@EnableDiscoveryClient
public class CommentsServiceApplication {
	
	@Bean
	public RestTemplate getRestTemplate() {
	    return new RestTemplate();
	  }

	public static void main(String[] args) {
		SpringApplication.run(CommentsServiceApplication.class, args);
	}

}
