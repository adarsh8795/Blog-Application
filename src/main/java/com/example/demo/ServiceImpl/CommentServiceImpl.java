package com.example.demo.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Binding.CommentForm;
import com.example.demo.Entity.CommentEntity;
import com.example.demo.Entity.PostEntity;
import com.example.demo.Repo.CommentRepo;
import com.example.demo.Repo.PostRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.CommentService;

import jakarta.servlet.http.HttpSession;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean addComment(CommentForm form, Integer id) {
		// TODO Auto-generated method stub
		CommentEntity comment = new CommentEntity();

		comment.setPostId(postRepo.findById(id).get());
		BeanUtils.copyProperties(form, comment);
		commentRepo.save(comment);
		return true;
	}

	@Override
	public List<CommentEntity> getCommentsByPostId(Integer id) {

		System.out.println(commentRepo.findAllByPostId(postRepo.findById(id).get()));

		return commentRepo.findAllByPostId(postRepo.findById(id).get());
	}

	@Override
	public List<CommentEntity> getAllCommentsByUser() {
		Integer userId = (Integer) session.getAttribute("userId");
		List<PostEntity> posts = postRepo.findAllByUserId(userRepo.findById(userId).get());
		List<CommentEntity> commentsList = new ArrayList<>();
		for (PostEntity postEntity : posts) {

			commentsList.addAll(commentRepo.findAllByPostId(postEntity));

		}
		System.out.println(commentsList);
		return commentsList;
	}

}
