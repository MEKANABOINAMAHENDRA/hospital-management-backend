package com.coders.hospitalmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer did;

	@NotBlank
	private String dname;

	@NotBlank
	private String specialization;

	@Pattern(regexp = "^[0-9]{10}$")
	private String phoneno;

	@Email
	private String email;

	@Positive
	private Integer experience;

	@NotBlank
	private String qualification;

	@ManyToOne
	private Department department;

	@NotNull
	private Boolean availability;

	// 🔥 VERY IMPORTANT (JWT LINK)
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Doctor() {
	}

	/* getters and setters */

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
