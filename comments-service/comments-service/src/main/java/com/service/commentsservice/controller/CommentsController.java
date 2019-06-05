package com.service.commentsservice.controller;

import java.util.List;

import javax.validation.Valid;

import com.duplicate.microservices.hazelcast.cache.HazelcastClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.commentsservice.models.Comment;
import com.service.commentsservice.repository.CommentRepository;
import com.service.commentsservice.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	
	@Autowired
	private CommentService commentRep;

	@Autowired
	private HazelcastClientTemplate hazelcastClientTemplate;
	/*public CommentsController(CommentRepository comment){
		this.commentRep = commentRep;
	}*/
	
	@RequestMapping(value = "/allComments", method = RequestMethod.GET)
	public List<Comment> AllComments(){
		return commentRep.getAllComments();
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public List<Comment> CommentsByUsername(@PathVariable String username) {
		return commentRep.getComments(username);
	}
	
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public void AddComment(@Valid @RequestBody Comment text) {
		commentRep.postComments(text);
	}
	/*@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public List<Comment> FindAll() {
		return (List<Comment>) commentRep.findAll();
	}*/

	@RequestMapping(value = "/cache/{username}", method = RequestMethod.GET)
	public Comment CacheCommentsByUsername(@PathVariable String username) {
		return hazelcastClientTemplate.getCacheCommentByUserName(username);
	}

	@RequestMapping(value = "/cache/addComment", method = RequestMethod.POST)
	public void AddCacheComment(@Valid @RequestBody Comment text) {
		hazelcastClientTemplate.putCacheCommentByUserName(text);
	}
}
