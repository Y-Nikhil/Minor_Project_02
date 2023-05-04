package com.ashokit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.AddEnquiryForm;
import com.ashokit.binding.DashboardResponse;
import com.ashokit.binding.SearchCriteria;
import com.ashokit.entity.Courses;
import com.ashokit.entity.Employee;
import com.ashokit.entity.Enquiry;
import com.ashokit.entity.EnquiryStatus;
import com.ashokit.repository.CoursesRepository;
import com.ashokit.repository.EmployeeRepository;
import com.ashokit.repository.EnquiryRepository;
import com.ashokit.repository.EnquiryStatusRepository;
import com.ashokit.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private HttpSession session;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private CoursesRepository coursesRepo;

	@Autowired
	private EnquiryStatusRepository statusRepo;

	@Autowired
	private EnquiryRepository enquiryRepo;

	@Override
	public DashboardResponse getDashBoardData(Integer employeeId) {
		DashboardResponse response = new DashboardResponse();

		Optional<Employee> findById = employeeRepo.findById(employeeId);

		if (findById.isPresent()) {

			Employee entity = findById.get();
			List<Enquiry> enquiries = entity.getListOfEnquiry();
			Integer totalCount = enquiries.size();

			Integer enrolledCount = enquiries.stream().filter(e -> e.getStatus().equalsIgnoreCase("enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCount = enquiries.stream().filter(e -> e.getStatus().equalsIgnoreCase("lost"))
					.collect(Collectors.toList()).size();
			response.setTotalEnquiryCnt(totalCount);
			response.setEnrolledCnt(enrolledCount);
			response.setLostCnt(lostCount);
		}
		return response;
	}

	@Override
	public boolean addEnquiry(AddEnquiryForm form, Integer employeeId) {
		
		Optional<Employee> findById = employeeRepo.findById(employeeId);
		Employee employee = findById.get();
		
		if(form.getEnquiryId() != null) {
			Enquiry enquiry = enquiryRepo.findById(form.getEnquiryId()).get();
			enquiry.setClassMode(form.getClassMode());
			enquiry.setCourse(form.getCourse());
			enquiry.setStatus(form.getStatus());
			enquiry.setStudentName(form.getStudentName());
			enquiry.setMobileNo(form.getMobileNo());
			enquiry.setEmployeeId(employee);			
			enquiryRepo.save(enquiry);
			return true;
		}
		Enquiry enqEntity = new Enquiry();
		BeanUtils.copyProperties(form, enqEntity);
		Employee empEntity = employeeRepo.findById(employeeId).get();
		enqEntity.setEmployeeId(empEntity);
		enquiryRepo.save(enqEntity);
		form.setEnquiryId(enqEntity.getEnquiryId());
		return true;
	}

	@Override
	public List<Enquiry> getFilteredEnquiries(Integer employeeId, SearchCriteria criteria) {
		Optional<Employee> findById = employeeRepo.findById(employeeId);
		if (findById.isPresent()) {
			Employee employee = findById.get();
			List<Enquiry> enquiries = employee.getListOfEnquiry();

			if (null != criteria.getCourse() && !"".equals(criteria.getCourse())) {
				enquiries = enquiries.stream().filter(e -> e.getCourse().equalsIgnoreCase(criteria.getCourse()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getStatus() && !"".equals(criteria.getStatus())) {
				enquiries = enquiries.stream().filter(e -> e.getStatus().equalsIgnoreCase(criteria.getStatus()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equalsIgnoreCase(criteria.getClassMode()))
						.collect(Collectors.toList());
			}
			return enquiries;
		}

		return null;
	}

	@Override
	public List<String> courseNames() {
		List<Courses> courses = coursesRepo.findAll();
		List<String> names = new ArrayList<String>();

		for (Courses entity : courses)
			names.add(entity.getCourseName());
		return names;
	}

	@Override
	public List<String> enquiryStatus() {
		List<EnquiryStatus> status = statusRepo.findAll();
		List<String> names = new ArrayList<String>();

		for (EnquiryStatus entity : status)
			names.add(entity.getEnquiryStatus());
		return names;
	}

	@Override
	public List<Enquiry> getEnquiries() {
		Integer employeeId = (Integer) session.getAttribute("employeeId");
		Optional<Employee> findAll = employeeRepo.findById(employeeId);
		if (findAll.isPresent()) {
			Employee enquiry = findAll.get();
			List<Enquiry> enquiries = enquiry.getListOfEnquiry();

			return enquiries;
		}
		return null;
	}
}
