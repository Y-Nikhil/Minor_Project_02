package com.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class EnquiryStatus {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer enquiryStatusId;
	private String enquiryStatus;
}
