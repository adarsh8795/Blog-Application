package com.example.demo.Service;

import java.util.List;

import com.example.demo.Binding.CommentForm;
import com.example.demo.Entity.CommentEntity;

public interface CommentService {

	public boolean addComment(CommentForm form, Integer id);

	public List<CommentEntity> getCommentsByPostId(Integer id);

	public List<CommentEntity> getAllCommentsByUser();

}
