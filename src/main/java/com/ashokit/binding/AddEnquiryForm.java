package com.ashokit.binding;

import lombok.Data;

@Data
public class AddEnquiryForm {

	private Integer enquiryId;
	private String studentName;
	private String course;
	private Long mobileNo;
	private String classMode;
	private String status;
}
