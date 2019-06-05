package com.service.commentsservice.service;

import java.util.List;

import com.service.commentsservice.models.Comment;

public interface CommentService {

	//List<Comment> findAll();
	List<Comment> getAllComments();
	List<Comment> getComments(String username);
	void postComments(Comment text);
	//List<Comment> findAll();
}
