package com.demo.app.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.app.dtos.UserRequestDto;
import com.demo.app.models.User;
import com.demo.app.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getAll() {		
		return userRepository.findAll();
	}

	@Override
	public User getById(Integer id) {
		User user = userRepository.getOne(id);
		return user;
	}

	@Override
	public User create(UserRequestDto userRequestDto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userRequestDto, User.class);
		User createdUser = userRepository.save(user);
		return createdUser;
	}

	@Override
	public User update(Integer id, UserRequestDto userRequestDto) {
		User user = getById(id);
		ModelMapper mapper = new ModelMapper();
		mapper.map(userRequestDto, user);
		user = userRepository.save(user);
		return user;
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
}
