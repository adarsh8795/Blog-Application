package com.example.demo.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Binding.LoginForm;
import com.example.demo.Binding.RegisterForm;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {

		UserEntity user = userRepo.findByEmail(form.getEmail());
		if (user == null) {
			return "User does not exist";

		}
		if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
			session.setAttribute("userId", user.getId());
			return "success";
		}
		return "Invalid credentials";
	}

	@Override
	public String register(RegisterForm form) {

		if (userRepo.findByEmail(form.getEmail()) != null) {
			return "email already exist";
		}
		System.out.println("register user " + userRepo.findByEmail(form.getEmail()));
		UserEntity user = new UserEntity();
		form.setPassword(passwordEncoder.encode(form.getPassword()));
		BeanUtils.copyProperties(form, user);
		System.out.println("user" + user);
		userRepo.save(user);
		return "success";
	}

}
