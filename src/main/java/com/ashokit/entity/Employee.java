package com.ashokit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Employee {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer employeeId;
	
	
	private String name;
	
	private String email;
	private String password;
	
	private Long mobileNo;
	private String status;
	
	@OneToMany(mappedBy = "employeeId", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<Enquiry> listOfEnquiry ;
}
