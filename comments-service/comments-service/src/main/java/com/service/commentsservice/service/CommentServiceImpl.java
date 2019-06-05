package com.service.commentsservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.service.commentsservice.mapper.CommentsMapper;
import com.service.commentsservice.models.Comment;
import com.service.commentsservice.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository repository;

	/*@Override
	public List<Comment> findAll() {
		return (List<Comment>) repository.findAll();
	}*/
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String GET_USER_COMMENTS = "SELECT * FROM comments WHERE username = ?;";
	
	private static final String GET_ALL_COMMENTS = "SELECT * FROM comments;";
	
	private static final String POST_COMMENT = "INSERT INTO comments(username, text, date) VALUES (?, ?, ?);";
	
	@Override
	public List<Comment> getAllComments(){
		List<Comment> comments = jdbcTemplate.query(GET_ALL_COMMENTS, new Object[] {}, new CommentsMapper());
		return comments;
	}
	
	@Override
	public List<Comment> getComments(String username){
		List<Comment> comments = jdbcTemplate.query(GET_USER_COMMENTS, new Object[]{username}, new CommentsMapper());
		return comments;
	}

	@Override
	public void postComments(Comment text) {
		jdbcTemplate.update(POST_COMMENT,
                new Object[]{text.getUsername(), text.getText(), text.getDate()});
	}


}
