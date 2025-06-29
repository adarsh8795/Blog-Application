package com.example.demo.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Binding.CreateBlogForm;
import com.example.demo.Entity.PostEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repo.PostRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.PostService;

import jakarta.servlet.http.HttpSession;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserRepo userRepo;

	@Override
	public String createPost(CreateBlogForm form) {
		// TODO Auto-generated method stub

		PostEntity post = new PostEntity();
		BeanUtils.copyProperties(form, post);
		Integer id = (Integer) session.getAttribute("userId");
		UserEntity user = userRepo.findById(id).get();
		post.setUserId(user);
		post.setStatus("Active");
		post.setPostedBy(user.getFirstName());
		System.out.println(post);
		postRepo.save(post);

		return "post Added";
	}

	@Override
	public List<PostEntity> geBlogsByUser(Integer id) {
		// TODO Auto-generated method stub
		UserEntity user = userRepo.findById(id).get();
		List<PostEntity> ls = postRepo.findAllByUserId(user);
		System.out.println(ls);
		return ls.stream().filter(post -> "Active".equalsIgnoreCase(post.getStatus())).collect(Collectors.toList());
	}

	@Override
	public List<PostEntity> getAllBlogs() {
		
		for (PostEntity post : postRepo.findAll()) {
		    if (post.getPostedBy() == null) {
		        post.setPostedBy(post.getUserId().getFirstName());
		        postRepo.save(post);
		    }
		}
		
		
		
		System.out.println("posts" + postRepo.findAll().stream()
				.filter(post -> "Active".equalsIgnoreCase(post.getStatus())).collect(Collectors.toList()));
		return postRepo.findAll().stream().filter(post -> "Active".equalsIgnoreCase(post.getStatus()))
				.collect(Collectors.toList());
	}
 
	
	
	@Override
	public PostEntity getBlogById(Integer id) {
		// TODO Auto-generated method stub

		postRepo.findById(id).get();
		return postRepo.findById(id).get();
	}

	@Override
	public PostEntity editPost(Integer id, CreateBlogForm form) {
		// TODO Auto-generated method stub
		PostEntity post = postRepo.findById(id).get();
		BeanUtils.copyProperties(form, post);
		postRepo.save(post);
		return post;
	}

	@Override
	public boolean deleteBlogById(Integer id) {
		PostEntity post = postRepo.findById(id).get();
		post.setStatus("Inactive");
		postRepo.save(post);
		return true;
	}

	@Override
	public List<PostEntity> searchBlog(String text) {
		// TODO Auto-generated method stub

		List<PostEntity> posts = postRepo.findAll();
		List<PostEntity> result = posts.stream().filter(p -> p.getTitle().toLowerCase().contains(text.toLowerCase()))
				.collect(Collectors.toList());
		System.out.println(result);

		return result;
	}

}
