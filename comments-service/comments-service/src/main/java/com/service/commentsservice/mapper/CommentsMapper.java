package com.service.commentsservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.service.commentsservice.models.Comment;

public class CommentsMapper implements RowMapper{

	
	@Override
	public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong(1));
        comment.setUsername(resultSet.getString(2));
        comment.setText(resultSet.getString(3));
        comment.setDate(resultSet.getTimestamp(4).toLocalDateTime());
        
        return comment;
    }
	
}
