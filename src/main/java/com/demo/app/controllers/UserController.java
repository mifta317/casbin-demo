package com.demo.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.app.dtos.UserRequestDto;
import com.demo.app.models.User;
import com.demo.app.services.UserService;

@RestController
@RequestMapping(path = "users", produces = "application/json")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.getAll();		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getById(@PathVariable Integer id){
		User user = userService.getById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> create(@RequestBody UserRequestDto userRequestDto){
		User user = userService.create(userRequestDto);		
		return new ResponseEntity<User>(user, HttpStatus.CREATED);		
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody UserRequestDto userRequestDto){
		User user = userService.update(id, userRequestDto);		
		return new ResponseEntity<User>(user, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> delete(@PathVariable Integer id){
		userService.delete(id);		
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);		
	}
}
