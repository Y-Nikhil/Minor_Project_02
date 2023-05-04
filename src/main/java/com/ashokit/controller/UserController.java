package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) throws Exception {
		
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute("SuccMsg", "check your Email");
		}else {
			model.addAttribute("errorMsg", "Problem Occured");
		}
			
		return "signup";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlock") UnlockForm form, Model model) {
		
		if(form.getNewPassword().equals(form.getConfirmPassword())){
		
			boolean status = userService.unlock(form);
			
			if(status) {
				model.addAttribute("succmsg", "Your account unlocked successfully");
			}else {
				model.addAttribute("errmsg", "Given Temporary password is incorrect. check your Email");
			}
		}
		model.addAttribute("errMsg", "New password and confirm password sholud be same");
		return "unlock";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {
		String login = userService.login(form);
		if(login.contains("success")) {
			return "redirect:/dashboard";
		}else {
			model.addAttribute("errMsg", login);
			return "login";
		}
		
	}
	
	@PostMapping("/forget")
	public String forget(@RequestParam String email, Model model ) throws Exception{
		
			boolean password = userService.forgetPassword(email);
			if(password) {
				model.addAttribute("updateMsg", "Check your email to get the password");
			}else {
				model.addAttribute("errMsg", "The Entered email is not matching, please check your email.");
			}
		
		return "forget";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new SignUpForm());
		
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlock(@RequestParam("email") String email, Model model) {
		
		UnlockForm unlockObj = new UnlockForm();
		unlockObj.setEmail(email);
		
		model.addAttribute("unlock", unlockObj);
		return "unlock";
	}
	
	@GetMapping("/forget")
	
	public String forget() {
		return "forget";
	}
	
}
