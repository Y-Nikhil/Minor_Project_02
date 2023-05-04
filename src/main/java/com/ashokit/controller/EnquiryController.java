package com.ashokit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.AddEnquiryForm;
import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.SearchCriteria;
import com.ashokit.entity.Enquiry;
import com.ashokit.repository.EnquiryRepository;
import com.ashokit.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService enquiryService;

	@Autowired
	private EnquiryRepository enquiryRepo;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		Integer employeeId = (Integer) session.getAttribute("employeeId");
		DashboardResponse dashBoardData = enquiryService.getDashBoardData(employeeId);
		model.addAttribute("dashboard", dashBoardData);
		return "dashboard";
	}

	@GetMapping("/addEnquiry")
	public String addEnquiryForm(Model model) {
		init(model);
		model.addAttribute("formObj", new AddEnquiryForm());
		return "addEnquiry";
	}

	private void init(Model model) {
		List<String> course = enquiryService.courseNames();
		List<String> status = enquiryService.enquiryStatus();
		model.addAttribute("courseName", course);
		model.addAttribute("enquiryStatus", status);
	}

	@GetMapping("/viewEnquiries")
	public String viewEnquiriesPage(Model model) {
		init(model);
		model.addAttribute("search", new SearchCriteria());
		List<Enquiry> enquiries = enquiryService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "viewEnquiries";
	}

	@GetMapping("/filterEnquiries")
	public String getFilteredEnqs(@RequestParam("course") String course, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {

		SearchCriteria criteria = new SearchCriteria();
		criteria.setCourse(course);
		criteria.setStatus(status);
		criteria.setClassMode(mode);

		Integer employeeId = (Integer) session.getAttribute("employeeId");
		List<Enquiry> filteredEnquiries = enquiryService.getFilteredEnquiries(employeeId, criteria);

		model.addAttribute("enquiries", filteredEnquiries);

		return "filterEnquiries";
	}

	@PostMapping("/addEnquiry")
	public String addEnquiry(@ModelAttribute("formObj") AddEnquiryForm form, Model model) {
		
		init(model);
		Integer employeeId = (Integer) session.getAttribute("employeeId");
		boolean addEnquiry = enquiryService.addEnquiry(form,employeeId);
		
		if (addEnquiry)
			model.addAttribute("succMsg", "Student Enquiry succefully added...");
		else
			model.addAttribute("errMsg", "Error");
		model.addAttribute("formObj", new AddEnquiryForm());
		return "addEnquiry";
	}

	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("enquiryId") Integer enquiryId, Model model) {
		Optional<Enquiry> findById = enquiryRepo.findById(enquiryId);
		if (findById.isPresent()) {
			Enquiry enquiry = findById.get();
			init(model);
			model.addAttribute("formObj", enquiry);
		}
		return "addEnquiry";
	}

}
