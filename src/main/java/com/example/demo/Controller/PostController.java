package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Binding.CommentForm;
import com.example.demo.Binding.CreateBlogForm;
import com.example.demo.Entity.PostEntity;
import com.example.demo.Repo.PostRepo;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private HttpSession session;

	@GetMapping("/userdashboard")
	public String login(Model model) {
		Integer id = (Integer) session.getAttribute("userId");
		List<PostEntity> userblogs = postService.geBlogsByUser(id);
		model.addAttribute("userblogs", userblogs);
		return "BlogList";
	}

	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Integer id, Model model) {
		model.addAttribute("blog", postService.getBlogById(id));
		model.addAttribute("comment", new CommentForm());
		model.addAttribute("comments", commentService.getCommentsByPostId(id));
		model.addAttribute("blogid", id);
		return "Blog";

	}

	@GetMapping("/createblog")
	public String create(Model model) {
		model.addAttribute("createBlogForm", new CreateBlogForm());

		return "CreateBlog";
	}

	@PostMapping("/createblog")
	public String handleCreate(@ModelAttribute("createBlogForm") CreateBlogForm form, Model model) {
		System.out.println(form);
		model.addAttribute("msg", "Blog added");
		postService.createPost(form);

		return "redirect:/userdashboard";
	}

	@GetMapping("/editblog/{id}")
	public String editBlog(Model model, @PathVariable Integer id) {

		model.addAttribute("createBlogForm", postService.getBlogById(id));
		model.addAttribute("postId", id);

		return "EditBlog";
	}

	@PostMapping("/editblog/edit")
	public String handleEdit(@ModelAttribute("createBlogForm") CreateBlogForm form, Model model,
			@RequestParam("postId") Integer id) {
		System.out.println(form);
		model.addAttribute("msg", "Changes Saved Successfully");
		postService.editPost(id, form);

		return "EditBlog";
	}

	@GetMapping("/deleteblog/{id}")
	public String delete(Model model, @PathVariable Integer id) {
		postService.deleteBlogById(id);

		return "BlogList";
	}

	@PostMapping("/comment")
	public String getComment(CommentForm form, Model model, @RequestParam("blogid") Integer id) {
		System.out.println(form);
		commentService.addComment(form, id);
		model.addAttribute("comment", new CommentForm());

		return "redirect:/blog/" + id;
	}

	@GetMapping("/comments")
	public String comments(Model model) {

		model.addAttribute("comments", commentService.getAllCommentsByUser());

		return "CommentList";
	}

	@GetMapping("/search")
	@ResponseBody
	public List<PostEntity> search(@RequestParam("searchText") String text) {
		System.out.println(text);
		System.out.println(postService.searchBlog(text));
		return postService.searchBlog(text);
	}

}
