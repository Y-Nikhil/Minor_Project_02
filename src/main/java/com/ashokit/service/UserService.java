package com.ashokit.service;

import org.springframework.stereotype.Service; 

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;

@Service
public interface UserService {

	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form) throws Exception;
	
	public boolean unlock(UnlockForm form);
	
	public boolean forgetPassword(String email) throws Exception;
}
