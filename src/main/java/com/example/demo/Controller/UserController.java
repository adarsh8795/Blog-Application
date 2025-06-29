package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Binding.LoginForm;
import com.example.demo.Binding.RegisterForm;
import com.example.demo.Entity.PostEntity;
import com.example.demo.Service.PostService;
import com.example.demo.Service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@GetMapping("/")
	public String homepage(Model model) {
		System.out.println(postService.getAllBlogs());
		model.addAttribute("blogs", postService.getAllBlogs());
		
		return "Index";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "Register";
	}

	@PostMapping("/register")
	public String handleRegister(Model model, RegisterForm form) {
		model.addAttribute("registerForm", form);
		userService.register(form);
		System.out.println(form);
		if ("User does not exist".equalsIgnoreCase(userService.register(form))) {
			
			return "Register";
		}
		model.addAttribute("msg", userService.register(form));
		return "redirect:/login";

	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "Login";
	}

	@PostMapping("/login")
	public String handleLogin(Model model, LoginForm form) {
		model.addAttribute("registerForm", form);
		String msg = userService.login(form);

		model.addAttribute("msg", msg);

		if (msg == "success") {
			return "redirect:/userdashboard";
		}
		return "Login";

	}

}
