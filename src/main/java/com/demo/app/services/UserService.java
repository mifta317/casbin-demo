package com.demo.app.services;

import java.util.List;

import com.demo.app.dtos.UserRequestDto;
import com.demo.app.models.User;

public interface UserService {
	
	public List<User> getAll();
	
	public User getById(Integer id);
	
	public User create(UserRequestDto userRequestDto);
	
	public User update(Integer id, UserRequestDto userRequestDto);
	
	public void delete(Integer id);

}
