package com.example.demo.Service;

import com.example.demo.Binding.LoginForm;
import com.example.demo.Binding.RegisterForm;

public interface UserService {

	public String login(LoginForm form);

	public String register(RegisterForm form);

}
