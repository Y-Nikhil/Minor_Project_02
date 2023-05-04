package com.ashokit.Runner;

import java.util.Arrays; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ashokit.entity.Courses;
import com.ashokit.entity.EnquiryStatus;
import com.ashokit.repository.CoursesRepository;
import com.ashokit.repository.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CoursesRepository coursesRepo;
	
	@Autowired
	private EnquiryStatusRepository statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		coursesRepo.deleteAll();
		
		Courses c1 = new Courses();
		c1.setCourseName("Java");
		
		Courses c2 = new Courses();
		c2.setCourseName("Python");
		
		Courses c3 = new Courses();
		c3.setCourseName("Devops");
		
		Courses c4 = new Courses();
		c4.setCourseName("AWS");
		
		coursesRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		statusRepo.deleteAll();
		
		EnquiryStatus s1 = new EnquiryStatus();
		s1.setEnquiryStatus("New");
		
		EnquiryStatus s2 = new EnquiryStatus();
		s2.setEnquiryStatus("Enrolled");
		
		EnquiryStatus s3 = new EnquiryStatus();
		s3.setEnquiryStatus("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
	}
	
	
}
