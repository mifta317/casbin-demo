package com.demo.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.app.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
