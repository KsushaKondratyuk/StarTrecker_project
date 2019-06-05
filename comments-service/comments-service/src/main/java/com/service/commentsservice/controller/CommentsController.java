package com.service.commentsservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	/*public CommentsController(CommentRepository comment){
		this.commentRep = commentRep;
	}*/
	
	@RequestMapping(value = "/allComments", method = RequestMethod.GET)
	public List<Comment> AllComments(){
		return commentRep.getAllComments();
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public List<Comment> CommentsByUsername(String username) {
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
	

}
