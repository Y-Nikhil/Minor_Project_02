package com.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashokit.binding.AddEnquiryForm;
import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.SearchCriteria;
import com.ashokit.entity.Enquiry;

@Service
public interface EnquiryService {

	public DashboardResponse getDashBoardData(Integer employeeId);

	public boolean addEnquiry(AddEnquiryForm form, Integer employeeID);
	
	public List<String> courseNames();
	
	public List<String> enquiryStatus();
	
	public List<Enquiry> getEnquiries();

	public List<Enquiry> getFilteredEnquiries(Integer employeeId, SearchCriteria search);
	
}
