package com.ashokit.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.entity.Employee;
import com.ashokit.repository.EmployeeRepository;
import com.ashokit.service.UserService;
import com.ashokit.utility.PasswordGenerator;
import com.ashokit.utility.emailSender;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private EmployeeRepository repo;
	
	@Autowired
	private emailSender mail;
	
	@Autowired
	private PasswordGenerator password;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm form) {
		Employee entity = repo.findByEmailAndPassword(form.getEmail(), form.getPassword());
		
		if(entity == null) {
			return "Invalid credentials";
		}
		if(entity.getStatus().equals("LOCKED")) {
			return "Account Locked";
		}
		
		session.setAttribute("employeeId", entity.getEmployeeId());
		
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm form) throws Exception {
		
		Employee email = repo.findByEmail(form.getEmail());
		if(email != null) {
			
			return false;
		}
		
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		String tempPassword = password.generatePassword();
		employee.setPassword(tempPassword);
		employee.setStatus("LOCKED");
		repo.save(employee);
		
		String to = form.getEmail();
		String subject = "Unlock Your Account";
		StringBuffer body =  new StringBuffer("");
		body.append("<h3>Use below temporary paasword to unlock your account</h3>");
		body.append("<br>");
		body.append("<p>Temporary password : <B>"+tempPassword+"+</B> </p>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your account</a>");
		mail.sendEmail(to, subject, String.valueOf(body));
		return true;
	}

	@Override
	public boolean unlock(UnlockForm form) {
		Employee employee = repo.findByEmail(form.getEmail());
		if(employee.getPassword().equals(form.getTempPassword())) {
			employee.setPassword(form.getConfirmPassword());
			employee.setStatus("UNLOCKED");
			repo.save(employee);
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public boolean forgetPassword(String email) throws Exception {
		Employee entity = repo.findByEmail(email);
		if(entity == null) {
			return false;
		}else{
			String subject ="Recover password";
			String body="Your Password :"+entity.getPassword();
			boolean send = mail.sendEmail(email, subject, body);
			if(send)   
				System.out.println("send successfull");
			else
				System.out.println("Error");
			return true;
		}
		
	}

	
}
