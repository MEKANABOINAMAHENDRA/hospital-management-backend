package com.coders.hospitalmanagement.dto;

public class DoctorResponseDTO {
	private Integer id;
	private String name;
	private String specialization;
	private String phoneNo;
	private Integer experience;
	private String qualification;
	private Boolean available;
	private Integer departmentId;
	private String departmentName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Integer getDepartmentId() {
	    return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
	    this.departmentId = departmentId;
	}

	public String getDepartmentName() {
	    return departmentName;
	}

	public void setDepartmentName(String departmentName) {
	    this.departmentName = departmentName;
	}


}
