package com.kstm.betterlife.form;

public class ContactForm {
	private int contact_id;
	private String name;
	private String mobile;
	private String phone;
	private String email;

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

	public String getPhones()
	{
		String phones="";
		if(this.phone!="")phones=this.phone;
		if(this.mobile!=""){
			if (phones!="")phones+=","+this.mobile;
			else phones=this.mobile;
		}
		if(phones!="")phones="{"+phones+"}";
		return phones;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

