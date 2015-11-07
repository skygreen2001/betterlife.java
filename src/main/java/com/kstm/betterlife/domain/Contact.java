package com.kstm.betterlife.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bb_core_contact")
public class Contact implements Serializable{
	private static final long serialVersionUID = -1799320413894606646L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_id")
	private int contact_id;
	@Column(name="name")
    private String name;
	@Column(name="phones")
    private String phones;
	@Column(name="email")
    private String email;

	private String mobile;
	private String phone;
	
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
