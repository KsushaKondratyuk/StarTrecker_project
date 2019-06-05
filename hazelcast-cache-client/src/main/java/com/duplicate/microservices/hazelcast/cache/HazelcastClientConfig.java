package com.duplicate.microservices.hazelcast.cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastClientConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientConfig cfg = new ClientConfig();

        ClientNetworkConfig network = cfg.getNetworkConfig();
        network.addAddress("hazelcast-cache", "hazelcast-cache:5701");


        return HazelcastClient.newHazelcastClient(cfg);
    }

    @Bean
    public HazelcastClientTemplate hazelcastClientTemplate() {
        return new HazelcastClientTemplate(hazelcastInstance());
    }

}
