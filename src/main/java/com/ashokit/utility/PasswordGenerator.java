package com.ashokit.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

	public String generatePassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random( 8, characters );
		
		return pwd;
	}
	
}
