package com.example.demo.Service;

import java.util.List;

import com.example.demo.Binding.CreateBlogForm;
import com.example.demo.Entity.PostEntity;

public interface PostService {

	public String createPost(CreateBlogForm form);

	public List<PostEntity> geBlogsByUser(Integer id);

	public List<PostEntity> getAllBlogs();

	public PostEntity getBlogById(Integer id);

	public PostEntity editPost(Integer id, CreateBlogForm form);

	public boolean deleteBlogById(Integer id);

	public List<PostEntity> searchBlog(String text);

	
}
