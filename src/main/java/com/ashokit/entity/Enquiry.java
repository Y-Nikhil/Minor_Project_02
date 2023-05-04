package com.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class Enquiry {

	@Id
	@GeneratedValue  (strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	
	private String studentName;
	private String course;
	private Long mobileNo;
	@CreationTimestamp
	private LocalDate enquiryCreatedDate;
	@UpdateTimestamp
	private LocalDate enquiryUpdatedDate;
	private String status;
	private String classMode;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn( name="employeeId")
	private Employee employeeId;
}
