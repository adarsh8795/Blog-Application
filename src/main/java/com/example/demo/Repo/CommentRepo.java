package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.CommentEntity;
import com.example.demo.Entity.PostEntity;

public interface CommentRepo extends JpaRepository<CommentEntity, Integer> {

	public List<CommentEntity> findAllByPostId(PostEntity post);

}
