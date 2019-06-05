package com.duplicate.microservices.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.grokonez.jwtauthentication.model.User;

public class HazelcastClientTemplate {

    private HazelcastInstance hazelcastInstance;

    public HazelcastClientTemplate(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public static final String USER_TOKEN_CACHE_MAP = "user_token_cache_map";

    public User getUserFromCacheByToken(String token) {
        return hazelcastInstance.<String, User>getMap(USER_TOKEN_CACHE_MAP).get(token);
    }

    public boolean getContainsUserInCacheByToken(String token) {
        return hazelcastInstance.<String, User>getMap(USER_TOKEN_CACHE_MAP).containsKey(token);
    }


    public User putUserToCacheByToken(String token, User user) {
        return hazelcastInstance.<String, User>getMap(USER_TOKEN_CACHE_MAP).put(token, user);
    }

}
