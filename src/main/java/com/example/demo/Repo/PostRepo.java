package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.PostEntity;
import com.example.demo.Entity.UserEntity;

public interface PostRepo extends JpaRepository<PostEntity, Integer> {

	List<PostEntity> findAllByUserId(UserEntity user);

}
