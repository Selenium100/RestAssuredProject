package com.qa.pojo;

import java.util.List;

import org.apache.juneau.annotation.Beanc;


public class AddPlace {
	
	private Integer accuracy;
	private String name;
	private String phone_number;
	private String address;
	private String website;
	private String language;
	private Location location;
	private List<String> types;
	
	
	
	@Beanc(properties = "location,accuracy,name,phone_number,types,address,website,language")
	public AddPlace(Location location,Integer accuracy, String name, String phone_number,List<String> types, String address, String website, String language
			) {
		
		this.accuracy = accuracy;
		this.name = name;
		this.phone_number = phone_number;
		this.address = address;
		this.website = website;
		this.language = language;
		this.location = location;
		this.types = types;
	}
	
	
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	
	

}
