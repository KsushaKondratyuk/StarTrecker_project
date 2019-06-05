package com.duplicate.microservices.hazelcast.cache;

import com.service.commentsservice.models.Comment;
import com.hazelcast.core.HazelcastInstance;
public class HazelcastClientTemplate {

    private HazelcastInstance hazelcastInstance;

    public HazelcastClientTemplate(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public static final String CACHE_COMMENT_MAP = "cache_comment_map";

    public Comment getCacheCommentByUserName(String userName) {
        return hazelcastInstance.<String, Comment>getMap(CACHE_COMMENT_MAP).get(userName);
    }

    public Comment putCacheCommentByUserName(Comment comment) {
        return hazelcastInstance.<String, Comment>getMap(CACHE_COMMENT_MAP).put(comment.getUsername(), comment);
    }

}
