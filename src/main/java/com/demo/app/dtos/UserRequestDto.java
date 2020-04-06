package com.demo.app.dtos;

public class UserRequestDto {

	private String name;
	private String city;
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return city;
	}
}
